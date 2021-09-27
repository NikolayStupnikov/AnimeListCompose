package ru.nikolay.stupnikov.animelistcompose.ui.filter

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import ru.nikolay.stupnikov.animelistcompose.di.component.ActivityComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.BaseActivity
import ru.nikolay.stupnikov.animelistcompose.ui.theme.AnimeListComposeTheme

class FilterActivity : BaseActivity<FilterViewModel>() {

    companion object {
        const val FILTER = "filter"
        const val FILTER_REQUEST_CODE = 1000
        val seasons = listOf("winter", "spring", "summer", "fall")
        val ageRatingList = listOf("G", "PG", "R", "R18")
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!mViewModel.vmIsWasAttached && intent.hasExtra(FILTER)) {
            val filter = intent.getSerializableExtra(FILTER) as Filter
            mViewModel.selectSeasons.addAll(filter.seasons)
            mViewModel.selectAgeRating.addAll(filter.ageRatingList)
            mViewModel.updateYear(filter.year)
            mViewModel.selectCategory = filter.category
        }

        setContent {
            AnimeListComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FilterActivityScreen(
                        mViewModel,
                        onBackPressed = this::onBackPressed,
                        submit = this::submitChanges,
                    )
                }
            }
        }

        mViewModel.vmIsWasAttached = true
    }

    private fun submitChanges() {
        val intent = Intent()
        val filter = Filter(
            mViewModel.selectSeasons,
            mViewModel.year,
            mViewModel.selectCategory,
            mViewModel.selectAgeRating
        )
        intent.putExtra(FILTER, filter)
        setResult(RESULT_OK, intent)
        finish()
    }
}

@Composable
private fun FilterActivityScreen(
    viewModel: FilterViewModel,
    onBackPressed: () -> Unit,
    submit: () -> Unit,
) {
    FilterPageContent(
        onBackPressed = onBackPressed,
        categoryList = viewModel.categoryList,
        selectCategory = viewModel.selectCategory,
        year = viewModel.year,
        clickButton = submit,
        selectSeasons = viewModel.selectSeasons,
        updateYear = viewModel::updateYear,
        selectAgeRating = viewModel.selectAgeRating,
        updateSelectCategory = viewModel::updateSelectCategory
    )
}