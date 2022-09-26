package ru.nikolay.stupnikov.animelistcompose.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.nikolay.stupnikov.animelistcompose.StaticConfig.PAGE_LIMIT
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.AnimeEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.model.AnimeItem

@Dao
abstract class AnimeDao {

    @Transaction
    open suspend fun insertAnimeAndHisTitle(
        animeList: List<AnimeEntity>,
        titleEntity: List<TitleEntity>
    ) {
        insertAnime(animeList)
        insertTitles(titleEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAnime(animeList: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTitles(titles: List<TitleEntity>)

    @Query("SELECT COUNT(*) FROM animeentity")
    abstract fun getCount(): Flow<Int>

    @Query("SELECT id, posterImage FROM animeentity " +
            "LIMIT $PAGE_LIMIT OFFSET :offset")
    abstract fun getAnimeList(offset: Int): Flow<List<AnimeItem>>

    @Query("SELECT * FROM AnimeEntity WHERE id = :id")
    abstract fun getDetails(id: Int): Flow<AnimeEntity>
}