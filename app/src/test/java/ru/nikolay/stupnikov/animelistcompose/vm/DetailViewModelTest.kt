package ru.nikolay.stupnikov.animelistcompose.vm

import androidx.compose.runtime.MutableState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nikolay.stupnikov.animelistcompose.TestAnimeApp
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerViewModelComponent
import ru.nikolay.stupnikov.animelistcompose.rule.RxImmediateSchedulerRule
import ru.nikolay.stupnikov.animelistcompose.ui.detail.DetailViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var description: MutableState<String>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
                .testAppComponent(TestAnimeApp.getAppComponent())
                .build()
                .inject(this)

        MockitoAnnotations.initMocks(this)
        detailViewModel.description = description
    }

    @Test
    fun getDetailsAnimeOnSuccess() {
        detailViewModel.getDetailsAnime(1)
        verify(description).value = ArgumentMatchers.anyString()
    }

    @Test
    fun getDetailsAnimeUnSuccess() {
        detailViewModel.getDetailsAnime(0)
        verify(description, never()).value = ArgumentMatchers.anyString()
    }
}