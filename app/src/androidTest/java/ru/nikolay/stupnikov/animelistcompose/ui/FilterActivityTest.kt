package ru.nikolay.stupnikov.animelistcompose.ui

import android.content.Intent
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import ru.nikolay.stupnikov.animelistcompose.AnimeApp
import ru.nikolay.stupnikov.animelistcompose.di.component.AppComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerAndroidTestAppComponent
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterActivity.Companion.FILTER
import ru.nikolay.stupnikov.animelistcompose.util.getActivity

class FilterActivityTest {

    private val intent: Intent

    init {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent

        intent = Intent(app, FilterActivity::class.java)
        intent.putExtra(FILTER, Filter(FilterActivity.seasons, "1984", "drama", listOf("R")))
    }

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<FilterActivity>, FilterActivity> =
        AndroidComposeTestRule(
            activityRule = ActivityScenarioRule(intent),
            activityProvider = { it.getActivity() }
        )

    @Test
    fun filter() {
        composeTestRule.onNode(hasTestTag("editText")).assertTextEquals("1984")
        composeTestRule.onNode(hasTestTag("spinner")).assertTextEquals("Drama")
        composeTestRule.onNode(hasTestTag("spinner")).performClick()
        composeTestRule.onNode(hasTestTag("4")).performClick()
        composeTestRule.onNode(hasTestTag("spinner")).assertTextEquals("Pirates")
    }
}