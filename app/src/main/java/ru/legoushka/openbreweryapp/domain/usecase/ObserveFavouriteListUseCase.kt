package ru.legoushka.openbreweryapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class ObserveFavouriteListUseCase @Inject constructor(
    private val repository: BreweryRepository
) {

    operator fun invoke(): Flow<List<Brewery>> = repository.observeFavorite()
}