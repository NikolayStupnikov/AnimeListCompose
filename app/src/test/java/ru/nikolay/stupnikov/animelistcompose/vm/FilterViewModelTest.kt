package ru.nikolay.stupnikov.animelistcompose.vm

import androidx.compose.runtime.snapshots.SnapshotStateList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nikolay.stupnikov.animelistcompose.TestAnimeApp
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.di.component.DaggerViewModelComponent
import ru.nikolay.stupnikov.animelistcompose.rule.RxImmediateSchedulerRule
import ru.nikolay.stupnikov.animelistcompose.ui.filter.FilterViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class FilterViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var filterViewModel: FilterViewModel

    @Mock
    lateinit var categoryList: SnapshotStateList<CategoryApi>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
            .testAppComponent(TestAnimeApp.getAppComponent())
            .build()
            .inject(this)

        MockitoAnnotations.initMocks(this)

        val categoryListData = FilterViewModel::class.java.getDeclaredField("categoryList")
        categoryListData.isAccessible = true
        categoryListData.set(filterViewModel, categoryList)
    }

    @Test
    fun getCategoryList() {
        val method = FilterViewModel::class.java.getDeclaredMethod("getCategoryList");
        method.isAccessible = true;
        method.invoke(filterViewModel)
    }
}