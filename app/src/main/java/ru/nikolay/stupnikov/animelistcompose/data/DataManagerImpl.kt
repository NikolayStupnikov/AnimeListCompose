package ru.nikolay.stupnikov.animelistcompose.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
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

    override fun requestAnimeList(offset: Int, search: String,  filter: Filter?): Flow<AnimeResponse> {
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
        return flow { emit(backendApi.requestAnimeList(params).getBody()) }
    }

    override fun requestCategoryList(offset: Int): Flow<CategoryResponse> {
        return flow { emit(backendApi.requestCategoryList(offset).getBody()) }
    }

    override fun getDetails(id: Int): Flow<DetailResponse> {
        return flow { emit(backendApi.getDetails(id).getBody()) }
    }

    override fun getCategoriesForAnime(id: Int): Flow<CategoryResponse> {
        return flow { emit(backendApi.getCategoriesForAnime(id).getBody()) }
    }

    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return database.categoryDao().getAll()
    }

    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        database.categoryDao().insert(categories)
    }

    override fun getCountCategories(): Flow<Int> {
        return database.categoryDao().getCount()
    }

    private fun <T> Response<T>.getBody(): T {
        return if (isSuccessful) {
                body() ?: error("Empty body")
            } else {
                error(errorBody()?.string() ?: "Server error")
            }
    }

}