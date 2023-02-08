package ru.legoushka.openbreweryapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.usecase.ChangeBreweryFavoriteStatusUseCase
import ru.legoushka.openbreweryapp.domain.usecase.GetBreweriesListPageUseCase
import ru.legoushka.openbreweryapp.domain.usecase.GetSearchMetadataUseCase
import ru.legoushka.openbreweryapp.domain.usecase.ObserveFavouriteListUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getBreweriesListPageUseCase: GetBreweriesListPageUseCase,
    private val getSearchMetadataUseCase: GetSearchMetadataUseCase,
    private val changeBreweryFavoriteStatusUseCase: ChangeBreweryFavoriteStatusUseCase,
    private val observeFavouriteListUseCase: ObserveFavouriteListUseCase
) : ViewModel() {



    private val _state = MutableStateFlow<MainScreenUiState>(MainScreenUiState())
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _state.value = _state.value.copy(isError = true)
    }

    init {
        loadPage()
        viewModelScope.launch(exceptionHandler) {
            observeFavouriteListUseCase().collect() {favorites ->
                _state.value = _state.value.copy(favorites = favorites)
            }
        }
    }

    fun loadPage(page: Int = 1, query: String = "") = viewModelScope.launch(exceptionHandler) {
        if (page >= 1) {
            _state.value = _state.value.copy(isLoading = true, isError = false)
            val breweryItems =
                getBreweriesListPageUseCase(page = page, query = query, _state.value.isFavorite)
            val searchMetadata = getSearchMetadataUseCase(query, _state.value.isFavorite)
            _state.value =
                _state.value.copy(
                    currentPage = page,
                    pagesTotal = searchMetadata.pagesTotal,
                    breweryItems = breweryItems,
                    isLoading = false,
                    isError = false
                )
        }
    }

    fun searchBarValueChange(value: String) {
        _state.value = _state.value.copy(searchBarValue = value)
    }

    fun onFavoriteFilter() {
        _state.value = _state.value.copy(isFavorite = !_state.value.isFavorite)
        loadPage(query = _state.value.searchBarValue)
    }

    fun changeDialogVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(isFilterDialogVisible = isVisible)
    }

    fun onBreweryFavorite(brewery: Brewery) {
        viewModelScope.launch (exceptionHandler) {
            changeBreweryFavoriteStatusUseCase(breweryId = brewery.id, _state.value.favorites.contains(brewery))
        }
    }



}


data class MainScreenUiState(
    val currentPage: Int = 1,
    val pagesTotal: Int = 1,
    val breweryItems: List<Brewery> = emptyList(),
    val favorites: List<Brewery> = emptyList(),
    val searchBarValue: String = "",
    val isFavorite: Boolean = false,
    val isFilterDialogVisible: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false
)
