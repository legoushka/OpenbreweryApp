package ru.legoushka.openbreweryapp.data.converter

import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.BreweryType
import javax.inject.Inject

class BreweryConverter @Inject constructor() {

    fun convert(from: BreweryModel): Brewery =
        Brewery(
            id = from.id,
            name = from.name,
            breweryType = convertBreweryType(from.breweryType),
            street = from.street,
            city = from.city,
            state = from.state,
            country = from.country,
            longitude = from.longitude,
            latitude = from.latitude,
            phone = from.phone,
            websiteUrl = from.websiteUrl
        )

    private fun convertBreweryType(type: String): BreweryType =
        when(type){
            "micro" -> BreweryType.MICRO
            "nano" -> BreweryType.NANO
            "regional" -> BreweryType.REGIONAL
            "brewpub" -> BreweryType.BREWPUB
            "large" -> BreweryType.LARGE
            "planning" -> BreweryType.PLANNING
            "bar" -> BreweryType.BAR
            "contract" -> BreweryType.CONTRACT
            "proprietor" -> BreweryType.PROPRIETOR
            "closed" -> BreweryType.CLOSED
            else -> BreweryType.CLOSED
        }
}