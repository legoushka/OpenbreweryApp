package ru.legoushka.openbreweryapp.domain.usecase

import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class GetBreweriesListPageUseCase @Inject constructor(
    private val repository: BreweryRepository
) {
    suspend operator fun invoke(page: Int, query: String = "", isFavorite: Boolean): List<Brewery> =
        repository.getListPage(page, query, isFavorite)
}