package ru.legoushka.openbreweryapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.usecase.ChangeBreweryFavoriteStatusUseCase
import ru.legoushka.openbreweryapp.domain.usecase.GetBreweryByIdUseCase
import ru.legoushka.openbreweryapp.domain.usecase.ObserveFavouriteListUseCase
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryDestinationArgs
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getBreweryByIdUseCase: GetBreweryByIdUseCase,
    private val changeBreweryFavoriteStatusUseCase: ChangeBreweryFavoriteStatusUseCase,
    private val observeFavouriteListUseCase: ObserveFavouriteListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val breweryId: String = savedStateHandle[BreweryDestinationArgs.BREWERY_ID_ARG]!!
    private val _state =
        MutableStateFlow<DetailScreenUiState>(DetailScreenUiState(breweryId = breweryId))
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _state.value = _state.value.copy(isError = true)
    }

    init {
        loadInfo()
        viewModelScope.launch(exceptionHandler) {
            observeFavouriteListUseCase().collect() { favorites ->
                _state.value = _state.value.copy(favorites = favorites)
            }
        }
    }

    fun loadInfo() = viewModelScope.launch(exceptionHandler) {
        _state.value = _state.value.copy(isLoading = true, isError = false)
        val result = getBreweryByIdUseCase(_state.value.breweryId)
        _state.value = _state.value.copy(brewery = result, isLoading = false, isError = false)
    }

    fun onBreweryFavorite() {
        viewModelScope.launch(exceptionHandler) {
            if (_state.value.brewery != null){
                changeBreweryFavoriteStatusUseCase(
                    breweryId = breweryId,
                    _state.value.favorites.contains(_state.value.brewery)
                )
            }
        }
    }
}


data class DetailScreenUiState(
    val breweryId: String,
    val brewery: Brewery? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val favorites: List<Brewery> = listOf()
)