package ru.nikolay.stupnikov.animelistcompose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AnimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["anime_id"],
            onDelete = CASCADE,
            deferred = true
        )
    ]
)
data class TitleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "en") val en: String?,
    @ColumnInfo(name = "en_jp") val enJp: String?,
    @ColumnInfo(name = "ja_jp") val jp: String?,
    @ColumnInfo(name = "anime_id") val animeId: Int
): Serializable