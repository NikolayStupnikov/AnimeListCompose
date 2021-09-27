package ru.nikolay.stupnikov.animelistcompose.data.api.response.detail

import com.google.gson.annotations.SerializedName

data class DetailApi(
    @SerializedName("attributes") val attributes: DetailAttribute?,
)