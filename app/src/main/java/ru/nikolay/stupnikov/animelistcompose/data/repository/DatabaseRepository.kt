package ru.nikolay.stupnikov.animelistcompose.data.repository

import kotlinx.coroutines.flow.Flow
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity

interface DatabaseRepository {

    fun getAllCategories(): Flow<List<CategoryEntity>>
    suspend fun insertCategories(categories: List<CategoryEntity>)
    fun getCountCategories(): Flow<Int>
}