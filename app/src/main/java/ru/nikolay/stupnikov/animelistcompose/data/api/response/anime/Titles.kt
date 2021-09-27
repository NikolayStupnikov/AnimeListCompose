package ru.nikolay.stupnikov.animelistcompose.data.api.response.anime

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Titles(
    @SerializedName("en") val en: String?,
    @SerializedName("en_jp") val enJp: String?,
    @SerializedName("ja_jp") val jp: String?
): Serializable