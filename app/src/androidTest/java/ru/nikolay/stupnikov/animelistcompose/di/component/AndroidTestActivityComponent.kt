package ru.nikolay.stupnikov.animelistcompose.di.component

import dagger.Component
import ru.nikolay.stupnikov.animelistcompose.di.module.ActivityModule
import ru.nikolay.stupnikov.animelistcompose.di.scope.ActivityScope

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AndroidTestAppComponent::class])
interface AndroidTestActivityComponent: ActivityComponent