package ru.nikolay.stupnikov.animelistcompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.nikolay.stupnikov.animelistcompose.R
import ru.nikolay.stupnikov.animelistcompose.data.database.entity.TitleEntity
import ru.nikolay.stupnikov.animelistcompose.data.database.model.AnimeItem
import ru.nikolay.stupnikov.animelistcompose.ui.base.ActionBarComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.InputText
import ru.nikolay.stupnikov.animelistcompose.ui.base.NetworkImage
import ru.nikolay.stupnikov.animelistcompose.ui.theme.Gray

@Composable
fun MainPageContent(
    animeList: List<AnimeItem>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    doOnScroll: (Int, Boolean) -> Unit,
    clickFilter: () -> Unit,
    search: (String) -> Unit,
    onClick: (id: Int, titles: TitleEntity?) -> Unit,
    isTest: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        ActionBarComponent(
            title = stringResource(id = R.string.anime_list),
            actions = {
                IconButton(onClick = { clickFilter() }) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_filter),
                        contentDescription = stringResource(id = R.string.filter)
                    )
                } },
            content = {
                MainBodyContent(
                    animeList = animeList,
                    isRefreshing = isRefreshing,
                    refresh = refresh,
                    doOnScroll = doOnScroll,
                    search = search,
                    onClick = onClick,
                    isTest = isTest
                )
            }
        )
        if (animeList.isEmpty()) {
            EmptyList()
        }
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MainBodyContent(
    animeList: List<AnimeItem>,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    doOnScroll: (Int, Boolean) -> Unit,
    search: (String) -> Unit,
    onClick: (id: Int, titles: TitleEntity?) -> Unit,
    isTest: Boolean
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        /*InputText(
            onTextChange = search,
            hint = R.string.search,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text)
        )*/
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { refresh() },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            if (animeList.isNotEmpty()) {
                val listState = rememberLazyListState()
                LazyColumn(state = listState) {
                    items(animeList) { anime ->
                        ItemHolder(anime, onClick)
                    }
                }
                if (!isTest) {
                    doOnScroll(
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1,
                        listState.isScrollInProgress
                    )
                }
            } else {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight()) {
                    Spacer(modifier = Modifier.fillMaxWidth().height(1000.dp))
                }
            }
        }
    }
}

@Composable
fun ItemHolder(anime: AnimeItem, onClick: (id: Int, titles: TitleEntity?) -> Unit) {
    Column(Modifier.clickable { onClick(anime.id, anime.title) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.margin_medium),
                top = dimensionResource(id = R.dimen.margin_medium),
                end = dimensionResource(id = R.dimen.margin_medium)
            )
        ) {
            NetworkImage(
                anime.posterImage,
                Modifier.size(dimensionResource(id = R.dimen.logo_size)),
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_medium)))
            Text(
                text = if (anime.title != null) {
                    if (!anime.title.en.isNullOrEmpty()) {
                        anime.title.en
                    } else if (!anime.title.enJp.isNullOrEmpty()) {
                        anime.title.enJp
                    } else if (!anime.title.jp.isNullOrEmpty()) {
                        anime.title.jp
                    } else {
                        stringResource(R.string.no_name)
                    }
                } else stringResource(R.string.no_name),
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.testTag(anime.id.toString())
            )
        }
        Divider(
            color = Gray,
            thickness = dimensionResource(id = R.dimen.line_height),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium))
        )
    }

}

@Composable
fun EmptyList() {
    Text(
        text = stringResource(id = R.string.swipe_to_update),
        color = Color.Black,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.testTag("emptyText")
    )
}

