package ru.nikolay.stupnikov.animelistcompose.vm

import androidx.compose.runtime.snapshots.SnapshotStateList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nikolay.stupnikov.animelistcompose.TestAnimeApp
import ru.nikolay.stupnikov.animelistcompose.data.api.response.anime.AnimeApi
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerViewModelComponent
import ru.nikolay.stupnikov.animelistcompose.rule.RxImmediateSchedulerRule
import ru.nikolay.stupnikov.animelistcompose.ui.filter.Filter
import ru.nikolay.stupnikov.animelistcompose.ui.main.MainViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var animeList: SnapshotStateList<AnimeApi>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
            .testAppComponent(TestAnimeApp.getAppComponent())
            .build()
            .inject(this)

        MockitoAnnotations.initMocks(this)

        val animeListData = MainViewModel::class.java.getDeclaredField("animeList")
        animeListData.isAccessible = true
        animeListData.set(mainViewModel, animeList)
    }

    @Test
    fun restart() {
        val method = MainViewModel::class.java.getDeclaredMethod("restart");
        method.isAccessible = true;
        method.invoke(mainViewModel)
        Mockito.verify(animeList).addAll(ArgumentMatchers.anyList())
    }

    @Test
    fun searchOnSuccess() {
        mainViewModel.search("Death note")
        Mockito.verify(animeList).addAll(ArgumentMatchers.anyList())
    }

    @Test
    fun searchUnSuccess() {
        mainViewModel.search("12345678")
        Mockito.verify(animeList, never()).addAll(ArgumentMatchers.anyList())
    }

    @Test
    fun setFilterOnSuccess() {
        mainViewModel.setFilter(Filter(listOf("winter", "summer"), 2018.toString(), "pirate", listOf("PG", "R")))
        Mockito.verify(animeList).addAll(ArgumentMatchers.anyList())
    }

    @Test
    fun setFilterUnSuccess() {
        mainViewModel.setFilter(Filter(listOf("winter", "summer"), 2018.toString(), "pirate", listOf("R18")))
        Mockito.verify(animeList, never()).addAll(ArgumentMatchers.anyList())
    }
}