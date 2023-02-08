package ru.legoushka.openbreweryapp.data.source.local

import kotlinx.coroutines.flow.Flow
import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.data.model.SearchMetadataModel

interface BreweryLocalDataSource {

    fun observeFavorites(): Flow<List<BreweryModel>>

    suspend fun getListPage(page: Int, perPage: Int, searchQuery: String): List<BreweryModel>

    suspend fun saveBrewery(breweryModel: BreweryModel)

    suspend fun deleteBrewery(breweryId: String)

    suspend fun getSearchMetadata(searchQuery: String, itemsPerPage: Int): SearchMetadataModel

}