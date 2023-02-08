package ru.legoushka.openbreweryapp.domain.usecase

import ru.legoushka.openbreweryapp.domain.entity.SearchMetadata
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class GetSearchMetadataUseCase @Inject constructor(
    private val repository: BreweryRepository
) {

    suspend operator fun invoke(searchQuery: String, isFavorite: Boolean): SearchMetadata =
        repository.getSearchMetadata(searchQuery = searchQuery, isFavorite = isFavorite)
}