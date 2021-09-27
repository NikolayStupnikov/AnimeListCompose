package ru.nikolay.stupnikov.animelistcompose.di.module

import dagger.Module
import dagger.Provides
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainViewModel

@Module
class TestActivityModule {

    @Provides
    fun provideMainViewModel(dataManager: DataManager): MainViewModel {
        return MainViewModel(dataManager)
    }

    @Provides
    fun provideFilterViewModel(dataManager: DataManager): FilterViewModel {
        return FilterViewModel(dataManager)
    }

    @Provides
    fun provideDetailViewModel(dataManager: DataManager): DetailViewModel {
        return DetailViewModel(dataManager)
    }
}