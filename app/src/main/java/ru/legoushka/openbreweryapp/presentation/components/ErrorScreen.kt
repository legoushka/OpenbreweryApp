package ru.legoushka.openbreweryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme


@Composable
fun ErrorScreen(
    isError: Boolean,
    errorTitle: String,
    isOnBackVisible: Boolean = false,
    onBack: () -> Unit = {},
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    AnimatedVisibility(
        visible = isError,
        enter = fadeIn(animationSpec = tween(100)),
        exit = fadeOut(animationSpec = tween(100))
    )
    {
        Surface(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = errorTitle, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(paddingSmall))

                FilledTonalButton(
                    onClick = onTryAgain
                ) {
                    Text(text = stringResource(R.string.try_again))
                }
                if (isOnBackVisible) {
                    Spacer(modifier = Modifier.height(paddingSmall))
                    OutlinedButton(onClick = onBack) {
                        Text(text = stringResource(R.string.go_back_button))
                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewErrorScreen() {
    DefaultTheme {
        ErrorScreen(
            isError = true,
            errorTitle = "Test error",
            isOnBackVisible = true,
            onBack = {},
            onTryAgain = {})
    }
}