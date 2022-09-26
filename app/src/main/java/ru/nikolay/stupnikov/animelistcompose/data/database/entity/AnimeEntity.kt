package ru.nikolay.stupnikov.animelistcompose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "ratingRank") val ratingRank: Int,
    @ColumnInfo(name = "startDate") val startDate: String?,
    @ColumnInfo(name = "endDate") val endDate: String?,
    @ColumnInfo(name = "ageRating") val ageRating: String?,
    @ColumnInfo(name = "ageRatingGuide") val ageRatingGuide: String?,
    @ColumnInfo(name = "episodeCount") val episodeCount: Int,
    @ColumnInfo(name = "episodeLength") val episodeLength: Int,
    @ColumnInfo(name = "posterImage") val posterImage: String?
)