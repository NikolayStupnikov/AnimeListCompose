package ru.nikolay.stupnikov.animelistcompose.ui.filter

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import ru.nikolay.stupnikov.animelistcompose.R
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryApi
import ru.nikolay.stupnikov.animelistcompose.data.api.response.category.CategoryAttribute
import ru.nikolay.stupnikov.animelistcompose.ui.base.ActionBarComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.InputText
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun FilterPageContent(
    onBackPressed: () -> Unit,
    categoryList: List<CategoryApi>,
    selectCategory: String?,
    updateSelectCategory: (String?) -> Unit,
    year: String,
    updateYear: (year: String) -> Unit,
    clickButton: () -> Unit,
    selectSeasons: ArrayList<String>,
    selectAgeRating: ArrayList<String>
){
    ActionBarComponent(
        title = stringResource(id = R.string.filter),
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        content = {
            FilterBodyContent(
                clickButton = clickButton,
                selectSeasons = selectSeasons,
                updateYear = updateYear,
                year = year,
                selectAgeRating = selectAgeRating,
                categoryList = categoryList,
                selectCategory = selectCategory,
                updateSelectCategory = updateSelectCategory
            )
        }
    )
}

@Composable
fun FilterBodyContent(
    clickButton: () -> Unit,
    selectSeasons: ArrayList<String>,
    updateYear: (year: String) -> Unit,
    year: String,
    selectAgeRating: ArrayList<String>,
    categoryList: List<CategoryApi>,
    selectCategory: String?,
    updateSelectCategory: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .weight(1f)
        ) {
            NameSection(text = R.string.seasons)
            CheckBoxBlock(list = FilterActivity.seasons, selectList = selectSeasons)
            
            NameSection(text = R.string.year_start_show)
            InputText(
                onTextChange = updateYear,
                hint = R.string.enter_the_year,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number),
                maxLength = 4,
                value = year
            )
            
            NameSection(text = R.string.age_rating)
            CheckBoxBlock(list = FilterActivity.ageRatingList, selectList = selectAgeRating)
            
            NameSection(text = R.string.categories)
            if (categoryList.isEmpty()) {
                CategoriesSelection(
                    categories = Collections.emptyList(),
                    selectCategory = null,
                    updateSelectCategory = updateSelectCategory
                )
            } else {
                CategoriesSelection(
                    categories = categoryList,
                    selectCategory = selectCategory,
                    updateSelectCategory = updateSelectCategory
                )
            }
        }
        Button(
            onClick = { clickButton() },
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.margin_small),
                bottom = dimensionResource(id = R.dimen.margin_small))) {
            Text(
                text = stringResource(id = R.string.submit).toUpperCase(Locale.ENGLISH),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_left_right_button),
                    end = dimensionResource(id = R.dimen.padding_left_right_button),
                    top = dimensionResource(id = R.dimen.padding_top_bottom_button),
                    bottom = dimensionResource(id = R.dimen.padding_top_bottom_button))
            )
        }
    }
}

@Composable
fun NameSection(@StringRes text: Int) {
    Text(
        text = stringResource(id = text),
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.margin_medium),
            top = dimensionResource(id = R.dimen.margin_medium)),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CheckBoxBlock(list: List<String>, selectList: ArrayList<String>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = dimensionResource(id = R.dimen.margin_left_checkbox)))
    {
        for (item in list) {
            CheckBoxItem(text = item, list = selectList)
        }
    }
}

@Composable
fun CheckBoxItem(text: String, list: ArrayList<String>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val isChecked = remember { mutableStateOf(list.contains(text)) }
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                if (it) {
                    list.add(text)
                } else {
                    list.remove(text)
                }
            }
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_small))
        )
    }
}

@Composable
fun CategoriesSelection(
    categories: List<CategoryApi>,
    selectCategory: String?,
    updateSelectCategory: (String?) -> Unit
) {
    val list = ArrayList<CategoryApi>()
    list.add(CategoryApi(CategoryAttribute(
        stringResource(R.string.all_categories),
        stringResource(R.string.all_categories)), 0)
    )
    list.addAll(categories)

    val text = remember { mutableStateOf(
         if (!selectCategory.isNullOrEmpty() && categories.isNotEmpty()) {
            val categoryApi: CategoryApi? =
                categories.firstOrNull { category -> category.attributes?.slug == selectCategory }
            if (categoryApi != null) {
                categoryApi.attributes?.title ?: categoryApi.attributes?.slug ?: ""
            } else {
                list[0].attributes!!.slug!!
            }
        } else {
             list[0].attributes!!.slug!!
        }
    )}

    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (CategoryApi) -> Unit = {
        text.value = it.attributes?.title ?: it.attributes?.slug ?: ""
        if (it.attributes?.slug == list[0].attributes?.slug) {
            updateSelectCategory(null)
        } else {
            updateSelectCategory(it.attributes?.slug)
        }
    }

    Column {
        Text(
            text = text.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.margin_medium),
                    top = dimensionResource(id = R.dimen.margin_small),
                    bottom = dimensionResource(id = R.dimen.margin_small)
                )
                .clickable(onClick = { isOpen.value = true })
        )
        DropDownList(
            requestToOpen = isOpen.value,
            list = list,
            openCloseOfDropDownList,
            userSelectedString
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<CategoryApi>,
    request: (Boolean) -> Unit,
    selectedString: (CategoryApi) -> Unit
) {
    DropdownMenu(
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(it.attributes?.title ?: it.attributes?.slug ?: "")
            }
        }
    }
}
