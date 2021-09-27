package ru.nikolay.stupnikov.animelistcompose.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import ru.nikolay.stupnikov.animelistcompose.R
import ru.nikolay.stupnikov.animelistcompose.ui.theme.EditTextBackgroundColor
import ru.nikolay.stupnikov.animelistcompose.ui.theme.HintColor

@Composable
fun NetworkImage(url: String?, modifier: Modifier) {
    if (!url.isNullOrEmpty()) {
        Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    placeholder(R.drawable.icon_logo)
                        .error(R.drawable.icon_logo)
                        .transformations(RoundedCornersTransformation(dimensionResource(id = R.dimen.radius_logo).value))
                }),
            contentDescription = stringResource(id = R.string.logo),
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(R.drawable.icon_logo),
            contentDescription = stringResource(id = R.string.logo),
            modifier = modifier
        )
    }
}

@Composable
fun ActionBarComponent(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = Color.Black
                    )
                },
                actions = actions,
                backgroundColor = Color.White,
                navigationIcon = navigationIcon
            )
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputText(
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    @StringRes hint: Int,
    maxLength: Int = 0,
    value: String = ""
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (text, setText) = remember { mutableStateOf(value) }
    TextField(
        value = text,
        onValueChange = {
            if (maxLength == 0 || it.length <= maxLength) {
                setText(it)
                onTextChange(it)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.margin_medium),
                top = dimensionResource(id = R.dimen.margin_top_edittext),
                end = dimensionResource(id = R.dimen.margin_medium),
                bottom = dimensionResource(id = R.dimen.margin_bottom_edittext)
            )
            .background(
                color = EditTextBackgroundColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_view))
            ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 15.sp
        ),
        placeholder = {
            Text(
                text = stringResource(id = hint),
                color = HintColor
            )
        }
    )
}