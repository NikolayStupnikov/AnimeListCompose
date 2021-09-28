package ru.nikolay.stupnikov.animelistcompose

import android.app.Application
import ru.nikolay.stupnikov.animelistcompose.di.component.AppComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerAppComponent

class AnimeApp : Application() {

    var isTest = false
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}