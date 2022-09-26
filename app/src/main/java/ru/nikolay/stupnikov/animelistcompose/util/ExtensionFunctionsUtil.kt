package ru.nikolay.stupnikov.animelistcompose.util

import retrofit2.Response
import java.lang.StringBuilder

fun List<String>.toSingleString(): String {
    val builder = StringBuilder()
    for (value in this) {
        builder.append("$value,")
    }
    return builder.toString().substring(0, builder.toString().length - 1)
}

fun <T> Response<T>.getBody(): T {
    return if (isSuccessful) {
        body() ?: error("Empty body")
    } else {
        error(errorBody()?.string() ?: "Server error")
    }
}
