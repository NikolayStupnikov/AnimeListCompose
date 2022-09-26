package ru.nikolay.stupnikov.animelistcompose.data.repository

import kotlinx.coroutines.flow.Flow
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryResponse
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter

interface NetworkRepository {

    fun requestAnimeList(offset: Int, search: String, filter: Filter?): Flow<AnimeResponse>
    fun requestCategoryList(offset: Int): Flow<CategoryResponse>
    fun getCategoriesForAnime(id: Int): Flow<CategoryResponse>
}