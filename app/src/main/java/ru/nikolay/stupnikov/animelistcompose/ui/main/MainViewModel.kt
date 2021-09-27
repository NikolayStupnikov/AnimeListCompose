package ru.nikolay.stupnikov.animelistcompose.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeApi
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter

class MainViewModel(private val dataManager: DataManager): BaseViewModel<MainNavigator>() {

    private var contentAnimeList: Disposable? = null

    var animeList = mutableStateListOf<AnimeApi>()
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
        compositeRemove(contentAnimeList)
        contentAnimeList = dataManager.requestAnimeList(offset, search, filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mIsLoading.value = false
                    if (it != null) {
                        if (!it.result.isNullOrEmpty()) {
                            animeList.addAll(it.result!!)
                            offset += StaticConfig.PAGE_LIMIT
                        }
                        if (it.meta != null) {
                            maxCount = it.meta!!.count
                        }
                    }
                }, {
                    mIsLoading.value = false
                    getNavigator()?.showError(showErrorMessage(it))
                })
        compositeAdd(contentAnimeList)
    }

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