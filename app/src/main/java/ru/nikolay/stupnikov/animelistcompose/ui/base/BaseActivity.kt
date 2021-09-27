package ru.nikolay.stupnikov.animelistcompose.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.nikolay.stupnikov.animelistcompose.AnimeApp
import ru.nikolay.stupnikov.animelistcompose.di.component.ActivityComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerActivityComponent
import ru.nikolay.stupnikov.animelistcompose.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<V : BaseViewModel<out Any>> :
    AppCompatActivity() {

    @Inject
    lateinit var mViewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
    }

    private val buildComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .appComponent((application as AnimeApp).appComponent)
            .activityModule(ActivityModule(this))
            .build()

    abstract fun performDependencyInjection(buildComponent: ActivityComponent)
}