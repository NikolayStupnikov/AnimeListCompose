package ru.nikolay.stupnikov.animelistcompose.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeAttribute(
        @SerializedName("titles") val titles: Titles?,
        @SerializedName("posterImage") val posterImage: Poster?,
        @SerializedName("description") val description: String?,
        @SerializedName("ratingRank") val ratingRank: Int,
        @SerializedName("startDate") val startDate: String?,
        @SerializedName("endDate") val endDate: String?,
        @SerializedName("ageRating") val ageRating: String?,
        @SerializedName("ageRatingGuide") val ageRatingGuide: String?,
        @SerializedName("episodeCount") val episodeCount: Int,
        @SerializedName("episodeLength") val episodeLength: Int
)