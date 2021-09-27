package ru.nikolay.stupnikov.animelistcompose.data.api.response.category

import com.google.gson.annotations.SerializedName
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.Meta

data class CategoryResponse(
        @SerializedName("data") var result: List<CategoryApi>?,
        @SerializedName("meta") var meta: Meta?
)