package ru.nikolay.stupnikov.animelistcompose.ui.filter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryAttribute
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel

class FilterViewModel(private val dataManager: DataManager): BaseViewModel<Any>() {

    private var contentCategoryCount: Job? = null
    private var contentCategoryList: Job? = null

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
        contentCategoryList?.let {
            if (it.isActive) it.cancel()
        }
        contentCategoryList = viewModelScope.launch {
            dataManager.requestCategoryList(offset).catch {
            }.collectLatest {
                it.meta?.let { meta ->
                    maxCount = meta.count
                }
                val categories = it.result
                if (!categories.isNullOrEmpty()) {
                    categoryList.addAll(categories)
                    offset += StaticConfig.PAGE_LIMIT

                    if (offset < maxCount) {
                        getCategoryList()
                    } else {
                        viewModelScope.launch {
                            dataManager.insertCategories(
                                categoryList.map { category ->
                                    CategoryEntity(
                                        category.id,
                                        category.attributes?.title,
                                        category.attributes?.slug
                                    )
                                }.toList()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategoryListFromDb() {
        contentCategoryList?.let {
            if (it.isActive) it.cancel()
        }
        contentCategoryList = viewModelScope.launch {
            dataManager.getAllCategories().catch {
            }.collectLatest {
                categoryList.addAll(
                    it.map { category ->
                        CategoryApi(
                            attributes = CategoryAttribute(category.title, category.slug),
                            id = category.id
                        )
                    }.toList()
                )
            }
        }
    }

    private fun getCategoryList() {
        contentCategoryCount?.let {
            if (it.isActive) it.cancel()
        }
        contentCategoryCount = viewModelScope.launch {
            dataManager.getCountCategories().catch {
            }.collectLatest {
                if (it > 0) {
                    getCategoryListFromDb()
                } else {
                    getCategoryListFromNetwork()
                }
            }
        }
    }

    fun updateYear(year: String) {
        this.year = year
    }

    fun updateSelectCategory(category: String?) {
        selectCategory = category
    }
}