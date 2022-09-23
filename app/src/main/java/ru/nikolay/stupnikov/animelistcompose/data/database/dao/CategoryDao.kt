package ru.nikolay.stupnikov.animelistcompose.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categoryentity")
    fun getAll(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("SELECT COUNT(*) FROM categoryentity")
    fun getCount(): Flow<Int>
}