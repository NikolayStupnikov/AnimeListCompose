package ru.nikolay.stupnikov.animelistcompose.ui

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import ru.nikolay.stupnikov.animelistcompose.AnimeApp
import ru.nikolay.stupnikov.animelistcompose.di.component.AppComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerAndroidTestAppComponent
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainActivity
import ru.nikolay.stupnikov.animelistcompose.util.getActivity

class MainActivityTest {

    companion object {
        const val SEARCH_INPUT = "Z"
    }

    private val intent: Intent

    init {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent
        app.isTest = true
        intent = Intent(app, MainActivity::class.java)
    }

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        AndroidComposeTestRule(
            activityRule = ActivityScenarioRule(intent),
            activityProvider = { it.getActivity() }
        )

    @Test
    fun listTest() {
        composeTestRule.onNode(hasTestTag("1"), useUnmergedTree = true).assertTextEquals("Death note")
        composeTestRule.onNode(hasTestTag("3"), useUnmergedTree = true).assertTextEquals("Cowboy Bebop")
    }

    @Test
    fun emptyText() {
        composeTestRule.onNode(hasTestTag("editText")).performTextInput(SEARCH_INPUT)
        composeTestRule.onNode(hasTestTag("emptyText")).assertIsDisplayed()
    }
}