package ru.nikolay.stupnikov.animelistcompose.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import ru.nikolay.stupnikov.animelistcompose.R
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.di.component.ActivityComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseActivity
import ru.nikolay.stupnikov.animelistcompose.ui.theme.AnimeListComposeTheme

class DetailActivity : BaseActivity<DetailViewModel>(), DetailNavigator {

    companion object {
        const val ID_ANIME = "id_anime"
        const val TITLES = "titles"
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        setContent {
            AnimeListComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DetailActivityScreen(
                        mViewModel,
                        getTitleToolbar(),
                        this::onBackPressed
                    )
                }
            }
        }

        if (!mViewModel.vmIsWasAttached) {
            mViewModel.getDetailsAnime(intent.getIntExtra(ID_ANIME, 0))
        }

        mViewModel.vmIsWasAttached = true
    }

    private fun getTitleToolbar(): String {
        val titles: TitleEntity? = intent.getSerializableExtra(TITLES) as TitleEntity?
        if (titles != null) {
            if (!titles.en.isNullOrEmpty()) {
                return titles.en
            } else if (!titles.enJp.isNullOrEmpty()) {
                return titles.enJp
            } else if (!titles.jp.isNullOrEmpty()) {
                return titles.jp
            }
        }
        return getString(R.string.no_name)
    }
}

@Composable
private fun DetailActivityScreen(
    viewModel: DetailViewModel,
    titleToolbar: String,
    onBackPressed: () -> Unit
) {
    DetailPageContent(
        isLoading = viewModel.mIsLoading.value,
        description = viewModel.description.value,
        rating = viewModel.rating.value,
        startDate = viewModel.startDate.value,
        endDate = viewModel.endDate.value,
        ageRating = viewModel.ageRating.value,
        episodeCount = viewModel.episodeCount.value,
        episodeLength = viewModel.episodeLength.value,
        categories = viewModel.categories.value,
        imageUrl = viewModel.imageUrl.value,
        onBackPressed = onBackPressed,
        titleToolbar = titleToolbar
    )
}