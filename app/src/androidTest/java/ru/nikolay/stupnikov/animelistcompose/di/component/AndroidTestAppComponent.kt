package ru.nikolay.stupnikov.animelistcompose.di.component

import dagger.Component
import ru.nikolay.stupnikov.animelistcompose.di.module.AndroidTestAppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidTestAppModule::class])
interface AndroidTestAppComponent: AppComponent