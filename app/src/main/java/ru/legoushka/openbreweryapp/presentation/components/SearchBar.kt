package ru.legoushka.openbreweryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    value: String,
    isDialogVisible: Boolean,
    isFavorite: Boolean,
    onValueChange: (String) -> Unit,
    onDialogDismiss: () -> Unit,
    onFilterIconClick: () -> Unit,
    onSearch: () -> Unit,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val keyboardController = LocalSoftwareKeyboardController.current

    FilterDialog(
        isShowed = isDialogVisible,
        onDismiss = onDialogDismiss,
        onFavorite = onFavorite,
        isFavorite = isFavorite
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
            onValueChange = {onValueChange(it)},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    keyboardController?.hide()
                }
            ),
            leadingIcon = {
                IconButton(
                    onClick = onFilterIconClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.filter)
                    )
                }
            },
            trailingIcon = {
                Row (modifier = Modifier.padding(end = paddingSmall)) {
                    FilledTonalIconButton(
                        onClick = {
                            onSearch()
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon)
                        )
                    }

                }
            },
            placeholder = {
                          Text(stringResource(R.string.brewery_search))
            },
            modifier = modifier.weight(1f)
        )


    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearchBar() {
    DefaultTheme() {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SearchBar(
                value = "example query",
                isDialogVisible = false,
                onValueChange = {},
                onDialogDismiss = {},
                onFilterIconClick = {},
                onSearch = {},
                isFavorite = false,
                onFavorite = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun PreviewEmptySearchBar() {
    DefaultTheme() {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SearchBar(
                value = "",
                isDialogVisible = false,
                onValueChange = {},
                onDialogDismiss = {},
                onFilterIconClick = {},
                onSearch = {},
                isFavorite = false,
                onFavorite = {}
            )
        }
    }
}