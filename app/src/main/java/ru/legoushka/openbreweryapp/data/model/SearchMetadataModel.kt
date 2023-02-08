package ru.legoushka.openbreweryapp.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMetadataModel(
    @SerialName("page")
    val page: String,
    @SerialName("per_page")
    val itemsPerPage: String,
    @SerialName("total")
    val total: String
)