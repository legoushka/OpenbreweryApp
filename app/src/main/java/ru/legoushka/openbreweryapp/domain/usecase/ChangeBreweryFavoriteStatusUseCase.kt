package ru.legoushka.openbreweryapp.domain.usecase

import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class ChangeBreweryFavoriteStatusUseCase @Inject constructor(
    private val repository: BreweryRepository
) {

    suspend operator fun invoke(breweryId: String, isFavorite: Boolean){
        repository.favoriteStatusChange(breweryId = breweryId, isFavorite = isFavorite)
    }

}