package ru.nikolay.stupnikov.animelistcompose.data.api.response.category

import com.google.gson.annotations.SerializedName

data class CategoryAttribute(
        @SerializedName("title") val title: String?,
        @SerializedName("slug") val slug: String?
)