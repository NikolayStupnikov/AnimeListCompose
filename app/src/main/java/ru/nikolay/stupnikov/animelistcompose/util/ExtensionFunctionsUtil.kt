package ru.nikolay.stupnikov.animelistcompose.util

import java.lang.StringBuilder

fun List<String>.toSingleString(): String {
    val builder = StringBuilder()
    for (value in this) {
        builder.append("$value,")
    }
    return builder.toString().substring(0, builder.toString().length - 1)
}