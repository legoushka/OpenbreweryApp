package ru.legoushka.openbreweryapp.domain.usecase

import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class GetBreweryByIdUseCase @Inject constructor(
    private val repository: BreweryRepository
) {

    suspend operator fun invoke (id: String) : Brewery =
        repository.getById(id)
}