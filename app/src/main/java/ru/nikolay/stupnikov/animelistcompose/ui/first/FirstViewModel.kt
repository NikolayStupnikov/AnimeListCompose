package ru.nikolay.stupnikov.animelistcompose.ui.first

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeApi
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.AnimeEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel

class FirstViewModel(
    private val dataManager: DataManager
): BaseViewModel<FirstPageNavigator>() {

    private var contentSizeList: Job? = null
    private var contentAnimeList: Job? = null

    private var offset = 0
    private val animeList = mutableListOf<AnimeApi>()

    fun goNextScreen() {
        mIsLoading.value = true
        contentSizeList?.let {
            if (it.isActive) it.cancel()
        }
        contentSizeList = viewModelScope.launch {
            mIsLoading.value = false
            dataManager.getCountAnime().catch {
                getNavigator()?.showError(showErrorMessage(it))
            }.collectLatest { count ->
                if (count > 0) {
                    getNavigator()?.goNextScreen()
                } else {
                    getAnimeList()
                }
                contentSizeList?.let {
                    if (it.isActive) it.cancel()
                }
            }
        }
    }

    private fun getAnimeList() {
        mIsLoading.value = true
        contentAnimeList?.let {
            if (it.isActive) it.cancel()
        }
        contentAnimeList = viewModelScope.launch {
            dataManager.requestAnimeList(offset, "", null).catch {
                mIsLoading.value = false
                clearDatabase()
                animeList.clear()
                offset = 0
                getNavigator()?.showError(showErrorMessage(it))
            }.collectLatest { animeResponse ->
                mIsLoading.value = false
                val list = animeResponse.result
                if (!list.isNullOrEmpty()) {
                    animeList.addAll(list)
                    offset += StaticConfig.PAGE_LIMIT
                    if (offset > 100) {
                        viewModelScope.launch {
                            dataManager.insertAnime(
                                animeList = animeList.mapToEntity(),
                                titles = animeList.getTitleEntity()
                            )
                            getNavigator()?.goNextScreen()
                        }
                    } else {
                        getAnimeList()
                    }
                }
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.clearDatabase()
        }
    }

    private fun List<AnimeApi>.mapToEntity(): List<AnimeEntity> =
        filter { it.attributes != null }
            .map { anime ->
                val attribute = anime.attributes!!
                AnimeEntity(
                    id = anime.id,
                    description = attribute.description,
                    ratingRank = attribute.ratingRank,
                    startDate = attribute.startDate,
                    endDate = attribute.endDate,
                    ageRating = attribute.ageRating,
                    ageRatingGuide = attribute.ageRatingGuide,
                    episodeCount = attribute.episodeCount,
                    episodeLength = attribute.episodeLength,
                    posterImage = attribute.posterImage?.original
                )
            }.toList()

    private fun List<AnimeApi>.getTitleEntity(): List<TitleEntity> =
        filter { it.attributes?.titles != null }
            .map { anime ->
                val titles = anime.attributes?.titles!!
                TitleEntity(
                    en = titles.en,
                    jp = titles.jp,
                    enJp = titles.enJp,
                    animeId = anime.id
                )
            }
}