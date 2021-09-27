package ru.nikolay.stupnikov.animelistcompose.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ru.nikolay.stupnikov.animelistcompose.R
import ru.nikolay.stupnikov.animelistcompose.ui.base.ActionBarComponent
import ru.nikolay.stupnikov.animelistcompose.ui.base.NetworkImage
import ru.nikolay.stupnikov.animelistcompose.ui.theme.DarkGray

@Composable
fun DetailPageContent(
    isLoading: Boolean,
    description: String,
    rating: Int,
    startDate: String,
    endDate: String,
    ageRating: String,
    episodeCount: Int,
    episodeLength: Int,
    categories: String,
    imageUrl: String,
    onBackPressed: () -> Unit,
    titleToolbar: String
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        ActionBarComponent(
            title = titleToolbar,
            navigationIcon = {
                IconButton(onClick = {
                    onBackPressed()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            content = {
                DetailBodyContent(
                    isLoading = isLoading,
                    description = description,
                    rating = rating,
                    startDate = startDate,
                    endDate = endDate,
                    ageRating = ageRating,
                    episodeCount = episodeCount,
                    episodeLength = episodeLength,
                    categories = categories,
                    imageUrl = imageUrl
                )
            }
        )
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DetailBodyContent(
    isLoading: Boolean,
    description: String,
    rating: Int,
    startDate: String,
    endDate: String,
    ageRating: String,
    episodeCount: Int,
    episodeLength: Int,
    categories: String,
    imageUrl: String
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .verticalScroll(scrollState)
    ) {
        if (!isLoading) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.margin_medium),
                        end = dimensionResource(id = R.dimen.margin_medium),
                        bottom = dimensionResource(id = R.dimen.margin_medium)
                    )
            ) {
                val (logo, descriptionText, titleRating, ratingText,
                    titleStartDate, startDateText, titleEndDate, endDateText,
                    titleAgeRating, ageRatingText, titleEpisodeCount, episodeCountText,
                    titleEpisodeLength, episodeLengthText, titleCategories, categoriesText) = createRefs()
                NetworkImage(
                    url = imageUrl,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.logo_big_size))
                        .constrainAs(logo) {
                            top.linkTo(
                                anchor = parent.top,
                                margin = 16.dp
                            )
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(descriptionText) {
                            top.linkTo(
                                anchor = logo.bottom,
                                margin = 16.dp
                            )
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                TitleText(
                    title = R.string.rating,
                    modifier = Modifier.constrainAs(titleRating) {
                        top.linkTo(
                            anchor = descriptionText.bottom,
                            margin = 16.dp
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = rating.toString(),
                    modifier = Modifier.constrainAs(ratingText) {
                    top.linkTo(
                        anchor = descriptionText.bottom,
                        margin = 16.dp
                    )
                    start.linkTo(titleRating.end)
                })

                TitleText(
                    title = R.string.start_date,
                    modifier = Modifier.constrainAs(titleStartDate) {
                        top.linkTo(
                            anchor = titleRating.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = startDate,
                    modifier = Modifier.constrainAs(startDateText) {
                        top.linkTo(
                            anchor = titleRating.bottom,
                        )
                        start.linkTo(titleStartDate.end)
                    }
                )

                TitleText(
                    title = R.string.end_date,
                    modifier = Modifier.constrainAs(titleEndDate) {
                        top.linkTo(
                            anchor = titleStartDate.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = endDate,
                    modifier = Modifier.constrainAs(endDateText) {
                        top.linkTo(
                            anchor = titleStartDate.bottom,
                        )
                        start.linkTo(titleEndDate.end)
                    }
                )

                TitleText(
                    title = R.string.age_rating_title,
                    modifier = Modifier.constrainAs(titleAgeRating) {
                        top.linkTo(
                            anchor = titleEndDate.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = ageRating,
                    modifier = Modifier.constrainAs(ageRatingText) {
                        top.linkTo(
                            anchor = titleEndDate.bottom,
                        )
                        start.linkTo(titleAgeRating.end)
                    }
                )

                TitleText(
                    title = R.string.episode_count,
                    modifier = Modifier.constrainAs(titleEpisodeCount) {
                        top.linkTo(
                            anchor = titleAgeRating.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = episodeCount.toString(),
                    modifier = Modifier.constrainAs(episodeCountText) {
                        top.linkTo(
                            anchor = titleAgeRating.bottom,
                        )
                        start.linkTo(titleEpisodeCount.end)
                    }
                )

                TitleText(
                    title = R.string.episode_length,
                    modifier = Modifier.constrainAs(titleEpisodeLength) {
                        top.linkTo(
                            anchor = titleEpisodeCount.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = episodeLength.toString(),
                    modifier = Modifier.constrainAs(episodeLengthText) {
                        top.linkTo(
                            anchor = titleEpisodeCount.bottom,
                        )
                        start.linkTo(titleEpisodeLength.end)
                    }
                )

                TitleText(
                    title = R.string.categories_title,
                    modifier = Modifier.constrainAs(titleCategories) {
                        top.linkTo(
                            anchor = titleEpisodeLength.bottom,
                        )
                        start.linkTo(parent.start)
                    }
                )
                BodyText(
                    text = categories,
                    modifier = Modifier.constrainAs(categoriesText) {
                        top.linkTo(
                            anchor = titleEpisodeLength.bottom,
                        )
                        start.linkTo(titleCategories.end)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    }
                )
            }
        }
    }
}

@Composable
fun TitleText(@StringRes title: Int, modifier: Modifier) {
    Text(
        text = stringResource(id = title),
        modifier = modifier,
        fontSize = 15.sp,
        color = DarkGray
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(start = 10.dp),
        fontSize = 15.sp,
        color = Color.Black
    )
}