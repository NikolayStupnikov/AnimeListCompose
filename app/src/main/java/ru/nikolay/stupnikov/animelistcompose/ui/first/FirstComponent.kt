package ru.nikolay.stupnikov.animelistcompose.ui.first

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nikolay.stupnikov.animelistcompose.R

@Composable
fun FirstPageContent(
    viewModel: FirstViewModel
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { viewModel.goNextScreen() },
                enabled = !viewModel.mIsLoading.value
            ) {
                Text(text =  stringResource(R.string.open_main_page))
            }
            Button(
                modifier = Modifier.padding(top = 20.dp),
                onClick = { viewModel.clearDatabase() }
            ) {
                Text(text =  stringResource(R.string.clear_base))
            }
        }
        if (viewModel.mIsLoading.value) {
            CircularProgressIndicator()
        }
    }
}
