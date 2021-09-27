package ru.nikolay.stupnikov.animelistcompose.data.api.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return "No Internet Connection"
    }
}