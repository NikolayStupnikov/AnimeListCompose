package ru.nikolay.stupnikov.animelistcompose.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import java.lang.StringBuilder

class DetailViewModel(private val dataManager: DataManager): BaseViewModel<DetailNavigator>() {

    var vmIsWasAttached = false
    private var contentDetails: Job? = null

    var description = mutableStateOf("")
    var rating = mutableStateOf(0)
    var startDate = mutableStateOf("")
    var endDate = mutableStateOf("")
    var ageRating = mutableStateOf("")
    var episodeCount = mutableStateOf(0)
    var episodeLength = mutableStateOf(0)
    var categories = mutableStateOf("")
    var imageUrl = mutableStateOf("")

    fun getDetailsAnime(id: Int) {
        mIsLoading.value = true
        contentDetails?.let {
            if (it.isActive) it.cancel()
        }
        contentDetails = viewModelScope.launch {
            dataManager.getDetails(id)
                .zip(dataManager.getCategoriesForAnime(id)) { details, categories ->
                    details to categories
                }.catch{
                    getNavigator()?.onBackPressed()
                }.collectLatest { (details, categoriesList) ->
                    mIsLoading.value = false
                    details.result?.attributes?.let { attributes ->
                        description.value = attributes.description ?: ""
                        rating.value = attributes.ratingRank
                        startDate.value = attributes.startDate ?: "-"
                        endDate.value = attributes.endDate ?: "-"
                        ageRating.value = "${attributes.ageRating}, ${attributes.ageRatingGuide}"
                        episodeCount.value = attributes.episodeCount
                        episodeLength.value = attributes.episodeLength
                        categories.value = getStringCategories(categoriesList.result)
                        imageUrl.value = attributes.posterImage?.original ?: ""
                    } ?: getNavigator()?.onBackPressed()
                }
        }
    }

    private fun getStringCategories(list: List<CategoryApi>?): String {
        if (list.isNullOrEmpty()) return "-"
        val builder = StringBuilder()
        for (category in list) {
            if (category.attributes != null && !category.attributes.title.isNullOrEmpty()) {
                builder.append("${category.attributes.title}, ")
            }
        }
        if (builder.isNotEmpty()) {
            return builder.toString().substring(0, builder.toString().length - 2)
        }
        return "-"
    }
}