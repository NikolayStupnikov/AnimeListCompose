package ru.nikolay.stupnikov.animelistcompose.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.nikolay.stupnikov.animelistcompose.StaticConfig.PAGE_LIMIT
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailResponse

interface BackendApi {

    @GET("anime")
    suspend fun requestAnimeList(@QueryMap params: Map<String, String>): Response<AnimeResponse>

    @GET("categories?page[limit]=$PAGE_LIMIT")
    suspend fun requestCategoryList(@Query("page[offset]") offset: Int): Response<CategoryResponse>

    @GET("anime/{id}")
    suspend fun getDetails(@Path("id") id: Int): Response<DetailResponse>

    @GET("anime/{id}/categories?page[limit]=$PAGE_LIMIT")
    suspend fun getCategoriesForAnime(@Path("id") id: Int): Response<CategoryResponse>
}