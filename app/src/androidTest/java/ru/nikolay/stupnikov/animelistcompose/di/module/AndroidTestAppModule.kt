package ru.nikolay.stupnikov.animelistcompose.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import org.mockito.Mockito
import ru.nikolay.stupnikov.animelistcompose.data.DataManager
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeAttribute
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.Titles
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryAttribute
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryResponse
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailAttribute
import ru.nikolay.stupnikov.animelistcompose.data.api.response.detail.DetailResponse
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.CategoryEntity
import ru.nikolay.stupnikov.animelistcompose.ui.DetailActivityTest.Companion.ANIME_ID
import ru.nikolay.stupnikov.animelistcompose.ui.DetailActivityTest.Companion.DESCRIPTION
import ru.nikolay.stupnikov.animelistcompose.ui.MainActivityTest
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Module
class AndroidTestAppModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager {
        val mock = Mockito.mock(DataManager::class.java)
        Mockito.`when`(mock.getDetails(ANIME_ID)).thenReturn(
                Single.just(
                        DetailResponse(
                                DetailApi(
                                        DetailAttribute(
                                        DESCRIPTION,
                                        123,
                                        "1998-04-03",
                                        "1999-04-24",
                                        "R",
                                        "17+ (violence & profanity)",
                                        26,
                                        25,
                                        null
                                        )))))
        Mockito.`when`(mock.getCategoriesForAnime(ANIME_ID)).thenReturn(
                Single.just(CategoryResponse(listOf(
                        CategoryApi(CategoryAttribute("Science Fiction", null), 1),
                        CategoryApi(CategoryAttribute("Space", null), 2),
                        CategoryApi(CategoryAttribute("Drama", null), 3)
                ), null))
        )
        Mockito.`when`(mock.getCountCategories()).thenReturn(Single.just(3))
        Mockito.`when`(mock.getAllCategories()).thenReturn(Single.just(listOf(
                CategoryEntity(1, "Space", "space"),
                CategoryEntity(2, "Android", "android"),
                CategoryEntity(3, "Drama", "drama"),
                CategoryEntity(4, "Pirates", "pirates")
        )))
        val offset = 0
        val search = ""
        val filter: Filter? = null
        val animeList = ArrayList<AnimeApi>()
        animeList.add(AnimeApi(AnimeAttribute(Titles(en = "Death note", null, null), null), 1))
        animeList.add(AnimeApi(AnimeAttribute(Titles(en = "Code Geass", null, null), null), 2))
        animeList.add(AnimeApi(AnimeAttribute(Titles("Cowboy Bebop", "Cowboy Bebop", "カウボーイビバップ"), null), 3))
        Mockito.`when`(mock.requestAnimeList(offset, search, filter)).thenReturn(
            Single.just(AnimeResponse(animeList, null)))
        Mockito.`when`(mock.requestAnimeList(offset, MainActivityTest.SEARCH_INPUT, filter)).thenReturn(
            Single.just(AnimeResponse(Collections.emptyList(), null)))
        return mock
    }

}