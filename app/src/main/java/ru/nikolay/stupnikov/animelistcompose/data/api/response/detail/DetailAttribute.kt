package ru.nikolay.stupnikov.animelistcompose.data.api.response.detail

import com.google.gson.annotations.SerializedName
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.Poster

data class DetailAttribute(
        @SerializedName("description") val description: String?,
        @SerializedName("ratingRank") val ratingRank: Int,
        @SerializedName("startDate") val startDate: String?,
        @SerializedName("endDate") val endDate: String?,
        @SerializedName("ageRating") val ageRating: String?,
        @SerializedName("ageRatingGuide") val ageRatingGuide: String?,
        @SerializedName("episodeCount") val episodeCount: Int,
        @SerializedName("episodeLength") val episodeLength: Int,
        @SerializedName("posterImage") val posterImage: Poster?
)