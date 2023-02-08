package ru.legoushka.openbreweryapp.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.BreweryType
import ru.legoushka.openbreweryapp.presentation.DetailScreenViewModel
import ru.legoushka.openbreweryapp.presentation.components.BreweryTypeText
import ru.legoushka.openbreweryapp.presentation.components.ErrorScreen
import ru.legoushka.openbreweryapp.presentation.components.Loading
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = state.value.brewery?.name ?: stringResource(R.string.unknown_brewery),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(
                                    R.string.go_back_button
                                )
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = viewModel::onBreweryFavorite) {
                            Icon(
                                imageVector =
                                    if (state.value.favorites.contains(state.value.brewery)){
                                        Icons.Default.Favorite
                                    }
                                    else {
                                        Icons.Default.FavoriteBorder
                                    },
                                contentDescription = stringResource(
                                    R.string.favorite_icon_button
                                )
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            state.value.brewery?.let {
                DetailScreenContent(brewery = it, modifier = Modifier.padding(paddingValues))
            }
        }
        Loading(isLoading = state.value.isLoading)
        ErrorScreen(
            isError = state.value.isError,
            errorTitle = "Error",
            isOnBackVisible = true,
            onBack = onBack,
            onTryAgain = viewModel::loadInfo
        )
    }
}

@Composable
fun DetailScreenContent(
    brewery: Brewery,
    modifier: Modifier = Modifier
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val paddingLarge = dimensionResource(R.dimen.padding_large)
    val address = listOfNotNull(
        brewery.street,
        brewery.city,
        brewery.state,
        brewery.country
    ).joinToString(", ")

    Surface(modifier = modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(paddingLarge)) {
            Text(
                text = stringResource(R.string.brewery_type).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.alpha(0.5f)
            )
            BreweryTypeText(
                breweryType = brewery.breweryType,
                style = MaterialTheme.typography.titleLarge
            )
            if (address.isNotEmpty()) {
                Divider(modifier = Modifier.padding(vertical = paddingMedium))

                Text(
                    text = stringResource(R.string.address).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.alpha(0.5f)
                )
                Text(
                    text = address,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            if (
                !brewery.phone.equals(null)
                or !brewery.websiteUrl.equals(null)
            ) {
                Divider(modifier = Modifier.padding(vertical = paddingMedium))

                Text(
                    text = stringResource(R.string.contacts).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.alpha(0.5f)
                )
                brewery.phone?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = stringResource(
                                R.string.phone_number_icon
                            ),
                            modifier = Modifier.padding(paddingSmall)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                brewery.websiteUrl?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = stringResource(
                                R.string.website_icon
                            ),
                            modifier = Modifier.padding(paddingSmall)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

            }


        }

    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailScreenContent() {
    DefaultTheme {
        DetailScreenContent(
            brewery = Brewery(
                id = "madtree-brewing-cincinnati",
                name = "MadTree Brewing",
                breweryType = BreweryType.REGIONAL,
                street = "3301 Madison Rd",
                city = "Cincinnati",
                state = "Ohio",
                country = "United States",
                longitude = "-84.4239715",
                latitude = "39.1563725",
                phone = "5138368733",
                websiteUrl = "http://www.madtreebrewing.com",
            )
        )
    }
}

