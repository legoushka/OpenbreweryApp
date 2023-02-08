package ru.legoushka.openbreweryapp.domain.entity

data class Brewery(
    val id: String,
    val name: String,
    val breweryType: BreweryType,
    val street: String?,
    val city: String,
    val state: String?,
    val country: String,
    val longitude: String?,
    val latitude: String?,
    val phone: String?,
    val websiteUrl: String?,
)

