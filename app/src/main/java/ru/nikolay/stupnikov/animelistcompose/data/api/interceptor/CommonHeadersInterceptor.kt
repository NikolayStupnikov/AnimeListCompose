package ru.nikolay.stupnikov.animelistcompose.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class CommonHeadersInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept-Language", Locale.getDefault().language)
        builder.addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }
}