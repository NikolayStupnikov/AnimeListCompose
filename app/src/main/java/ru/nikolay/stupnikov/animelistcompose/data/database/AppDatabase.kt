package ru.nikolay.stupnikov.animelistcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikolay.stupnikov.animelistcompose.data.database.dao.CategoryDao
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}