package ru.nikolay.stupnikov.animelistcompose.ui.filter

import androidx.compose.runtime.mutableStateListOf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryAttribute
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import kotlin.concurrent.thread

class FilterViewModel(private val dataManager: DataManager): BaseViewModel<Any>() {

    private var contentCategoryCount: Disposable? = null
    private var contentCategoryList: Disposable? = null

    val selectSeasons: ArrayList<String> = ArrayList()
    val selectAgeRating: ArrayList<String> = ArrayList()

    var categoryList = mutableStateListOf<CategoryApi>()
        private set
    var selectCategory: String? = null

    var year = ""
        private set

    var vmIsWasAttached = false

    private var offset = 0
    private var maxCount = 0

    init {
        getCategoryList()
    }

    private fun getCategoryListFromNetwork() {
        compositeRemove(contentCategoryList)
        contentCategoryList = dataManager.requestCategoryList(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        if (it.meta != null) {
                            maxCount = it.meta!!.count
                        }
                        if (!it.result.isNullOrEmpty()) {
                            categoryList.addAll(it.result!!)
                            offset += StaticConfig.PAGE_LIMIT

                            if (offset < maxCount) {
                                getCategoryList()
                            } else {
                                thread {
                                    dataManager.insertCategories(categoryList.map {
                                            category -> CategoryEntity(
                                            category.id,
                                            category.attributes?.title,
                                            category.attributes?.slug
                                        )
                                    }.toList())
                                }
                            }
                        }
                    }
                }, {})
        compositeAdd(contentCategoryList)
    }

    private fun getCategoryListFromDb() {
        compositeRemove(contentCategoryList)
        contentCategoryList = dataManager.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryList.addAll(it.map {
                        category -> CategoryApi(
                        CategoryAttribute(category.title, category.slug),
                        category.id
                    )
                }.toList())
            }, {})
        compositeAdd(contentCategoryList)
    }

    private fun getCategoryList() {
        compositeRemove(contentCategoryCount)
        contentCategoryCount = dataManager.getCountCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it > 0) {
                    getCategoryListFromDb()
                } else {
                    getCategoryListFromNetwork()
                }
            }, {})
        compositeAdd(contentCategoryCount)
    }

    fun updateYear(year: String) {
        this.year = year
    }

    fun updateSelectCategory(category: String?) {
        selectCategory = category
    }
}