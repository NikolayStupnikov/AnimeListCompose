package ru.nikolay.stupnikov.animelistcompose.ui.first

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import ru.nikolay.stupnikov.animelistcompose.di.component.ActivityComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseActivity
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainActivity
import ru.nikolay.stupnikov.animelistcompose.ui.theme.AnimeListComposeTheme

class FirstActivity : BaseActivity<FirstViewModel>(), FirstPageNavigator {

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        setContent {
            AnimeListComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FirstPageContent(mViewModel)
                }
            }
        }
    }

    override fun goNextScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
