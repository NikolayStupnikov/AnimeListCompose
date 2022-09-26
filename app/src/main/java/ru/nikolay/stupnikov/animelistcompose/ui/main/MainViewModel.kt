package ru.nikolay.stupnikov.animelistcompose.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.database.model.AnimeItem
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter

class MainViewModel(private val dataManager: DataManager): BaseViewModel<MainNavigator>() {

    private var contentAnimeList: Job? = null

    var animeList = mutableStateListOf<AnimeItem>()
        private set

    var isRefreshing = mutableStateOf(false)

    private var offset = 0
    private var maxCount = 0
    private var search: String = ""
    var filter: Filter? = null
    private set

    init {
        getAnimeList()
    }

    private fun getAnimeList() {
        mIsLoading.value = true
        contentAnimeList?.let {
            if (it.isActive) it.cancel()
        }
        contentAnimeList = viewModelScope.launch {
            dataManager.getAnimeList(offset = offset, search = search)
                .zip(getMaxCountFlow()) { animeList, count ->
                    animeList to count
                }.catch {
                    mIsLoading.value = false
                    getNavigator()?.showError(showErrorMessage(it))
                }.collectLatest { (anime, count) ->
                    mIsLoading.value = false
                    if (anime.isNotEmpty()) {
                        animeList.addAll(anime)
                        offset += StaticConfig.PAGE_LIMIT
                    }
                    maxCount = count
                }
        }
    }

    private fun getMaxCountFlow(): Flow<Int> =
        if (maxCount == 0) dataManager.getCountAnime()
        else flow { emit(maxCount) }

    private fun restart() {
        offset = 0
        animeList.clear()
        getAnimeList()
    }

    fun search(search: String) {
        this.search = search
        restart()
    }

    fun setFilter(filter: Filter) {
        this.filter = filter
        restart()
    }

    fun refresh() {
        isRefreshing.value = true
        restart()
        isRefreshing.value = false
    }

    var scrollItemIndex = 0
    fun doOnScroll(lastVisibleElement: Int, isScrolling: Boolean) {
        if (!isScrolling) {
            if (animeList.size != 0 && lastVisibleElement == animeList.size -1
                && offset < maxCount && animeList.size != scrollItemIndex) {
                scrollItemIndex = animeList.size
                getAnimeList()
            }
        } else {
            scrollItemIndex = 0
        }
    }
}