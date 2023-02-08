package ru.legoushka.openbreweryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    isShowed: Boolean,
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddingLarge = dimensionResource(R.dimen.padding_large)

    if (isShowed) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Surface(shape = MaterialTheme.shapes.large) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingLarge)
                ) {
                    Text(text = stringResource(R.string.filter))
                    FilterChip(
                        selected = isFavorite,
                        label = { Text(text = stringResource(R.string.favorite)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = stringResource(R.string.favorite)
                            )
                        },
                        onClick = onFavorite
                    )
                    FilledTonalButton(onClick = onDismiss) {
                        Text(stringResource(R.string.close_dialog))
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterBar() {
    DefaultTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            FilterDialog(
                isShowed = true,
                onDismiss = {},
                onFavorite = {},
                isFavorite = true
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFilterBarFavorite() {
    DefaultTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            FilterDialog(
                isShowed = true,
                onDismiss = {},
                onFavorite = {},
                isFavorite = false
            )
        }
    }
}

