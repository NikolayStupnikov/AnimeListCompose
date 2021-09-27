package ru.nikolay.stupnikov.animelistcompose.di.component

import dagger.Component
import ru.nikolay.stupnikov.animelistcompose.di.module.TestActivityModule
import ru.nikolay.stupnikov.animelistcompose.di.scope.ActivityScope
import ru.nikolay.stupnikov.animelistcompose.vm.DetailViewModelTest
import ru.nikolay.stupnikov.animelistcompose.vm.FilterViewModelTest
import ru.nikolay.stupnikov.animelistcompose.vm.MainViewModelTest

@ActivityScope
@Component(modules = [TestActivityModule::class], dependencies = [TestAppComponent::class])
interface ViewModelComponent {

    fun inject(viewModel: MainViewModelTest)

    fun inject(viewModel: FilterViewModelTest)

    fun inject(viewModel: DetailViewModelTest)
}