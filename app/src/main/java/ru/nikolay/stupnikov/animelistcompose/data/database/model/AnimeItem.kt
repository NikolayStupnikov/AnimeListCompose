package ru.nikolay.stupnikov.animelistcompose.data.database.model

import androidx.room.Relation
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity

data class AnimeItem(
    val id: Int,
    val posterImage: String?,
    @Relation(parentColumn = "id", entityColumn = "anime_id")
    val title: TitleEntity?
)