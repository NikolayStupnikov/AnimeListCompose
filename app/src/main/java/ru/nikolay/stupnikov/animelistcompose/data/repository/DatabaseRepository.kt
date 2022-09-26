package ru.nikolay.stupnikov.animelistcompose.data.repository

import kotlinx.coroutines.flow.Flow
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.AnimeEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.model.AnimeItem

interface DatabaseRepository {

    fun getAllCategories(): Flow<List<CategoryEntity>>
    fun getCountCategories(): Flow<Int>

    suspend fun insertCategories(categories: List<CategoryEntity>)

    suspend fun clearDatabase()

    suspend fun insertAnime(animeList: List<AnimeEntity>, titles: List<TitleEntity>)
    fun getCountAnime(): Flow<Int>
    fun getAnimeList(offset: Int, search: String): Flow<List<AnimeItem>>
    fun getDetails(id: Int): Flow<AnimeEntity>
}