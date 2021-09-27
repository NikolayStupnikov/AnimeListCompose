package ru.nikolay.stupnikov.animelistcompose.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categoryentity")
    fun getAll(): Single<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<CategoryEntity>)

    @Query("SELECT COUNT(*) FROM categoryentity")
    fun getCount(): Single<Int>
}