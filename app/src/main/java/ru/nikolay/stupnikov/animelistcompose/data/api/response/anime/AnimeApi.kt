package ru.nikolay.stupnikov.animelistcompose.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeApi(
        @SerializedName("attributes") val attributes: AnimeAttribute?,
        @SerializedName("id") val id: Int
)