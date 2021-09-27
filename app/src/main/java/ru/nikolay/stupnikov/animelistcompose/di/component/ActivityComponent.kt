package ru.nikolay.stupnikov.animelistcompose.di.component

import dagger.Component
import ru.nikolay.stupnikov.animelistcompose.di.module.ActivityModule
import ru.nikolay.stupnikov.animelistcompose.di.scope.ActivityScope
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainActivity

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: DetailActivity)

    fun inject(activity: FilterActivity)
}