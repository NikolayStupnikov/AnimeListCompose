package ru.nikolay.stupnikov.animelistcompose.data.repository

import io.reactivex.Single
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailResponse
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter

interface NetworkRepository {

    fun requestAnimeList(offset: Int, search: String, filter: Filter?): Single<AnimeResponse>
    fun requestCategoryList(offset: Int): Single<CategoryResponse>
    fun getDetails(id: Int): Single<DetailResponse>
    fun getCategoriesForAnime(id: Int): Single<CategoryResponse>
}