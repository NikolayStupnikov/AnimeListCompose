package ru.nikolay.stupnikov.animelistcompose.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeAttribute(
        @SerializedName("titles") val titles: Titles?,
        @SerializedName("posterImage") val posterImage: Poster?
)