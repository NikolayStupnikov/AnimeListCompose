package ru.nikolay.stupnikov.animelistcompose.ui.detail

import androidx.compose.runtime.mutableStateOf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import ru.nikolay.stupnikov.animelistcompose.util.zipWith
import java.lang.StringBuilder

class DetailViewModel(private val dataManager: DataManager): BaseViewModel<DetailNavigator>() {

    var vmIsWasAttached = false
    private var contentDetails: Disposable? = null

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
        compositeRemove(contentDetails)
        contentDetails = dataManager.getDetails(id)
                .zipWith(dataManager.getCategoriesForAnime(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mIsLoading.value = false
                    if (it.first.result != null && it.first.result!!.attributes != null) {
                        description.value = it.first.result!!.attributes!!.description ?: ""
                        rating.value = it.first.result!!.attributes!!.ratingRank
                        startDate.value = it.first.result!!.attributes!!.startDate ?: "-"
                        endDate.value = it.first.result!!.attributes!!.endDate ?: "-"
                        ageRating.value = "${it.first.result!!.attributes!!.ageRating}, ${it.first.result!!.attributes!!.ageRatingGuide}"
                        episodeCount.value = it.first.result!!.attributes!!.episodeCount
                        episodeLength.value = it.first.result!!.attributes!!.episodeLength
                        categories.value = getStringCategories(it.second.result)
                        imageUrl.value = it.first.result!!.attributes!!.posterImage?.original ?: ""
                    } else {
                        getNavigator()?.onBackPressed()
                    }
                }, {
                    getNavigator()?.onBackPressed()
                })
        compositeAdd(contentDetails)
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