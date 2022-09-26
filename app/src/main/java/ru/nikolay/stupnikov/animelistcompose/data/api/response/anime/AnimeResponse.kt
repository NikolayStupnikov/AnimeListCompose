package ru.nikolay.stupnikov.animelistcompose.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
        @SerializedName("data") var result: List<AnimeApi>?
)