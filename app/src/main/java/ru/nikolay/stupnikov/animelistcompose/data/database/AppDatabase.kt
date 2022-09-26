package ru.nikolay.stupnikov.animelistcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikolay.stupnikov.animelistcompose.data.database.dao.AnimeDao
import ru.nikolay.stupnikov.animelistcompose.data.database.dao.CategoryDao
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.AnimeEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity

@Database(entities = [CategoryEntity::class, AnimeEntity::class, TitleEntity::class], version = 3,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun animeDao(): AnimeDao
}