package ru.nikolay.stupnikov.animelistcompose.data

import io.reactivex.Single
import ru.nikolay.stupnikov.animelistcompose.StaticConfig.PAGE_LIMIT
import ru.nikolay.stupnikov.animelistcompose.data.api.BackendApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailResponse
import ru.nikolay.stupnikov.animelistcompose.data.database.AppDatabase
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity
import ru.nikolay.stupnikov.animelistcompose.util.toSingleString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl
@Inject constructor(
    private val backendApi: BackendApi,
    private val database: AppDatabase
) : DataManager {

    override fun requestAnimeList(offset: Int, search: String,  filter: Filter?): Single<AnimeResponse> {
        val params = HashMap<String, String>()
        params["page[limit]"] = PAGE_LIMIT.toString()
        params["page[offset]"] = offset.toString()
        if (search.isNotEmpty()) {
            params["filter[text]"] = search
        }
        if (filter != null) {
            if (filter.seasons.isNotEmpty() && filter.seasons.size != FilterActivity.seasons.size) {
                params["filter[season]"] = filter.seasons.toSingleString()
            }
            if (filter.ageRatingList.isNotEmpty() && filter.ageRatingList.size != FilterActivity.ageRatingList.size) {
                params["filter[ageRating]"] = filter.ageRatingList.toSingleString()
            }
            if (filter.year.isNotEmpty()) {
                params["filter[seasonYear]"] = filter.year
            }
            if (!filter.category.isNullOrEmpty()) {
                params["filter[categories]"] = filter.category
            }
        }
        return backendApi.requestAnimeList(params)
    }

    override fun requestCategoryList(offset: Int): Single<CategoryResponse> {
        return backendApi.requestCategoryList(offset)
    }

    override fun getDetails(id: Int): Single<DetailResponse> {
        return backendApi.getDetails(id)
    }

    override fun getCategoriesForAnime(id: Int): Single<CategoryResponse> {
        return backendApi.getCategoriesForAnime(id)
    }

    override fun getAllCategories(): Single<List<CategoryEntity>> {
        return database.categoryDao().getAll()
    }

    override fun insertCategories(categories: List<CategoryEntity>) {
        database.categoryDao().insert(categories)
    }

    override fun getCountCategories(): Single<Int> {
        return database.categoryDao().getCount()
    }
}