package ru.nikolay.stupnikov.animelistcompose.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.nikolay.stupnikov.animelistcompose.ViewModelProviderFactory
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseActivity
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterViewModel
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainViewModel

@Module
class ActivityModule(private val activity: BaseActivity<out BaseViewModel<out Any>>) {

    @Provides
    fun provideMainViewModel(dataManager: DataManager): MainViewModel {
        val supplier: Supplier<MainViewModel> = Supplier { MainViewModel(dataManager) }
        val factory: ViewModelProviderFactory<MainViewModel> = ViewModelProviderFactory(
            MainViewModel::class.java,
            supplier
        )
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    @Provides
    fun provideDetailViewModel(dataManager: DataManager): DetailViewModel {
        val supplier: Supplier<DetailViewModel> = Supplier { DetailViewModel(dataManager) }
        val factory: ViewModelProviderFactory<DetailViewModel> = ViewModelProviderFactory(
            DetailViewModel::class.java,
            supplier
        )
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    @Provides
    fun provideFilterViewModel(dataManager: DataManager): FilterViewModel {
        val supplier: Supplier<FilterViewModel> = Supplier { FilterViewModel(dataManager) }
        val factory: ViewModelProviderFactory<FilterViewModel> = ViewModelProviderFactory(
            FilterViewModel::class.java,
            supplier
        )
        return ViewModelProvider(activity, factory).get(FilterViewModel::class.java)
    }
}