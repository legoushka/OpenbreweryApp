package ru.legoushka.openbreweryapp.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.legoushka.openbreweryapp.R
import kotlinx.coroutines.launch
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.BreweryType
import ru.legoushka.openbreweryapp.presentation.MainScreenViewModel
import ru.legoushka.openbreweryapp.presentation.components.*
import ru.legoushka.openbreweryapp.presentation.components.*
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onBreweryClick: (Brewery) -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text =
                        stringResource(R.string.top_bar_app_name_emoji) +
                                stringResource(R.string.app_name) +
                                stringResource(R.string.top_bar_app_name_emoji)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },

        ) { paddingValues ->
        Box {
            MainScreenContent(
                currentPage = state.value.currentPage,
                pagesTotal = state.value.pagesTotal,
                breweries = state.value.breweryItems,
                favorites = state.value.favorites,
                searchBarValue = state.value.searchBarValue,
                isDialogVisible = state.value.isFilterDialogVisible,
                isFavoriteFilter = state.value.isFavorite,
                onFilterIconClick = { viewModel.changeDialogVisibility(true) },
                onFavoriteFilter = viewModel::onFavoriteFilter,
                onDialogDismiss = { viewModel.changeDialogVisibility(false) },
                loadPage = viewModel::loadPage,
                onBreweryClick = onBreweryClick,
                onBreweryFavorite = viewModel::onBreweryFavorite,
                searchBarOnValueChange = viewModel::searchBarValueChange,
                onSearch = { viewModel.loadPage(query = state.value.searchBarValue) },
                modifier = Modifier.padding(paddingValues)
            )
            Loading(
                isLoading = state.value.isLoading,
                modifier = Modifier.padding(paddingValues)
            )
            ErrorScreen(
                isError = state.value.isError,
                errorTitle = stringResource(R.string.error),
                onTryAgain = { viewModel.loadPage(page = state.value.currentPage) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}


@Composable
fun MainScreenContent(
    currentPage: Int,
    pagesTotal: Int,
    breweries: List<Brewery>,
    favorites: List<Brewery>,
    searchBarValue: String,
    isDialogVisible: Boolean,
    isFavoriteFilter: Boolean,
    loadPage: (Int, String) -> Unit,
    onBreweryClick: (Brewery) -> Unit,
    onBreweryFavorite: (Brewery) -> Unit,
    onFavoriteFilter: () -> Unit,
    searchBarOnValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilterIconClick: () -> Unit,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingLarge = dimensionResource(R.dimen.padding_large)
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = paddingLarge)
    ) {
        Spacer(modifier = Modifier.height(paddingSmall))
        SearchBar(
            value = searchBarValue,
            isDialogVisible = isDialogVisible,
            isFavorite = isFavoriteFilter,
            onDialogDismiss = onDialogDismiss,
            onValueChange = searchBarOnValueChange,
            onFilterIconClick = onFilterIconClick,
            onFavorite = onFavoriteFilter,
            onSearch = {
                onSearch()
                if (breweries.isNotEmpty()) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(paddingSmall))
        PaginationBar(
            currentPage = currentPage,
            pagesTotal = pagesTotal,
            onNextPage = {
                loadPage(currentPage + 1, searchBarValue)
                coroutineScope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            },
            onPreviousPage = {
                loadPage(currentPage - 1, searchBarValue)
                coroutineScope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            },
        )
        Spacer(modifier = Modifier.height(paddingSmall))
        if (breweries.isEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.nothing_found),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            LazyColumn(state = listState) {
                items(breweries) { item ->
                    BreweryCard(
                        brewery = item,
                        isFavorite = favorites.contains(item),
                        onClick = onBreweryClick,
                        onFavorite = onBreweryFavorite
                    )
                    Spacer(modifier = Modifier.height(paddingLarge))

                }
            }
        }
    }


}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreenContent() {
    DefaultTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            MainScreenContent(
                loadPage = { _, _ -> },
                onBreweryClick = {},
                onFilterIconClick = {},
                searchBarOnValueChange = {},
                searchBarValue = "Brewery name",
                currentPage = 0,
                breweries = List(10) {
                    Brewery(
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
                },
                isDialogVisible = false,
                onDialogDismiss = {},
                onSearch = {},
                pagesTotal = 10,
                onBreweryFavorite = {},
                onFavoriteFilter = {},
                isFavoriteFilter = false,
                favorites = emptyList()
            )
        }
    }
}