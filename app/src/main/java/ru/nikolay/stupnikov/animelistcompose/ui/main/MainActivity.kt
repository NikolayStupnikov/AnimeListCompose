package ru.nikolay.stupnikov.animelistcompose.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import ru.nikolay.stupnikov.animelistcompose.AnimeApp
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.di.component.ActivityComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseActivity
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity.Companion.ID_ANIME
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity.Companion.TITLES
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity.Companion.FILTER
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity.Companion.FILTER_REQUEST_CODE
import ru.nikolay.stupnikov.animelistcompose.ui.theme.AnimeListComposeTheme

class MainActivity : BaseActivity<MainViewModel>(), MainNavigator {

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        setContent {
            AnimeListComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityScreen(
                        viewModel = mViewModel,
                        openFilter = this::openFilter,
                        onClick = this::clickItem,
                        isTest = (application as AnimeApp).isTest)
                }
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(FILTER)) {
                mViewModel.setFilter(data.getSerializableExtra(FILTER) as Filter)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun openFilter() {
        val intent = Intent(this, FilterActivity::class.java)
        if (mViewModel.filter != null) {
            intent.putExtra(FILTER, mViewModel.filter)
        }
        startActivityForResult(intent, FILTER_REQUEST_CODE)
    }

    private fun clickItem(id: Int, titles: TitleEntity?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(ID_ANIME, id)
        intent.putExtra(TITLES, titles)
        startActivity(intent)
    }
}

@Composable
private fun MainActivityScreen(
    viewModel: MainViewModel,
    openFilter: () -> Unit,
    onClick: (id: Int, titles: TitleEntity?) -> Unit,
    isTest: Boolean
) {
    MainPageContent(
        animeList = viewModel.animeList,
        isLoading = viewModel.mIsLoading.value,
        isRefreshing = viewModel.isRefreshing.value,
        refresh = viewModel::refresh,
        doOnScroll = viewModel::doOnScroll,
        clickFilter = openFilter,
        search = viewModel::search,
        onClick = onClick,
        isTest = isTest
    )
}