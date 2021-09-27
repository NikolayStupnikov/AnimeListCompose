package ru.nikolay.stupnikov.animelistcompose

import android.app.Application
import org.mockito.Mockito
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerTestAppComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.TestAppComponent

object TestAnimeApp {

    private lateinit var appComponent: TestAppComponent

    fun getAppComponent(): TestAppComponent {
        if (!this::appComponent.isInitialized) {
            appComponent = DaggerTestAppComponent.builder()
                .application(Mockito.mock(Application::class.java))
                .build()
        }
        return appComponent
    }
}