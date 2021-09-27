package ru.nikolay.stupnikov.animelistcompose.di.module

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikolay.stupnikov.animelistcompose.BuildConfig
import ru.nikolay.stupnikov.animelistcompose.StaticConfig
import ru.nikolay.stupnikov.animelistcompose.data.api.BackendApi
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.DataManagerImpl
import ru.nikolay.stupnikov.animelistcompose.data.database.AppDatabase
import ru.nikolay.stupnikov.animelistcompose.data.api.interceptor.CommonHeadersInterceptor
import ru.nikolay.stupnikov.animelistcompose.data.api.interceptor.NetworkConnectionInterceptor
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGSon(): Gson =
        GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(StaticConfig.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS)
            .writeTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS)

        okHttpClientBuilder.addInterceptor(NetworkConnectionInterceptor(application))
        okHttpClientBuilder.addInterceptor(CommonHeadersInterceptor())

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(application))
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)
        }
        val cacheDir = File(
                System.getProperty("java.io.tmpdir"),
                UUID.randomUUID().toString()
        )
        val cacheSize = 10 * 1024 * 1024
        try {
            val cache = Cache(cacheDir, cacheSize.toLong())
            okHttpClientBuilder.cache(cache)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            gson: Gson, okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun backendApi(retrofit: Retrofit): BackendApi {
        return retrofit.create(BackendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    @Singleton
    fun database(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "animeDb")
            .fallbackToDestructiveMigration()
            .build()
    }
}