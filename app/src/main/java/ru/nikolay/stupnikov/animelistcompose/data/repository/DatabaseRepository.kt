package ru.nikolay.stupnikov.animelistcompose.data.repository

import io.reactivex.Single
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity

interface DatabaseRepository {

    fun getAllCategories(): Single<List<CategoryEntity>>
    fun insertCategories(categories: List<CategoryEntity>)
    fun getCountCategories(): Single<Int>
}