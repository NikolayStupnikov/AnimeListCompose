package ru.nikolay.stupnikov.animelistcompose.ui

import android.content.Intent
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import ru.nikolay.stupnikov.animelistcompose.AnimeApp
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.Titles
import ru.nikolay.stupnikov.animelistcompose.di.component.AppComponent
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerAndroidTestAppComponent
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity.Companion.ID_ANIME
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailActivity.Companion.TITLES
import ru.nikolay.stupnikov.animelistcompose.util.getActivity

class DetailActivityTest {

    companion object {
        const val DESCRIPTION = "In the year 2071, humanity has colonized several of the planets and moons of the solar system leaving the now uninhabitable surface of planet Earth behind. The Inter Solar System Police attempts to keep peace in the galaxy, aided in part by outlaw bounty hunters, referred to as \\\"Cowboys\\\". The ragtag team aboard the spaceship Bebop are two such individuals.\\nMellow and carefree Spike Spiegel is balanced by his boisterous, pragmatic partner Jet Black as the pair makes a living chasing bounties and collecting rewards. Thrown off course by the addition of new members that they meet in their travels—Ein, a genetically engineered, highly intelligent Welsh Corgi; femme fatale Faye Valentine, an enigmatic trickster with memory loss; and the strange computer whiz kid Edward Wong—the crew embarks on thrilling adventures that unravel each member's dark and mysterious past little by little. \\nWell-balanced with high density action and light-hearted comedy, Cowboy Bebop is a space Western classic and an homage to the smooth and improvised music it is named after.\\n\\n(Source: MAL Rewrite)"
        const val ANIME_ID = 1
    }

    private val intent: Intent

    init {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent

        intent = Intent(app, DetailActivity::class.java)
        intent.putExtra(TITLES, Titles("Cowboy Bebop", "Cowboy Bebop", "カウボーイビバップ"))
        intent.putExtra(ID_ANIME, ANIME_ID)
    }

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<DetailActivity>, DetailActivity> =
        AndroidComposeTestRule(
            activityRule = ActivityScenarioRule(intent),
            activityProvider = { it.getActivity() }
        )

    @Test
    fun getDetails() {
        composeTestRule.onNode(hasContentDescription("toolbar")).assertTextEquals("Cowboy Bebop")
        composeTestRule.onNode(hasContentDescription("description")).assertTextEquals(DESCRIPTION)
    }
}