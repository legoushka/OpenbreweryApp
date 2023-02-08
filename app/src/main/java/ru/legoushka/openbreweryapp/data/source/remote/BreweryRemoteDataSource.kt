package ru.legoushka.openbreweryapp.data.source.remote

import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.data.model.SearchMetadataModel

interface BreweryRemoteDataSource {

    suspend fun getListPage(page: Int, itemsPerPage: Int, query: String): List<BreweryModel>

    suspend fun getById(id: String): BreweryModel

    suspend fun getSearchMetadata(searchQuery: String, itemsPerPage: Int): SearchMetadataModel
}