package ru.legoushka.openbreweryapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.SearchMetadata

interface BreweryRepository {

    fun observeFavorite(): Flow<List<Brewery>>
    suspend fun getListPage(page: Int, query: String, isFavorite: Boolean): List<Brewery>

    suspend fun getById(id: String): Brewery

    suspend fun getSearchMetadata(searchQuery: String, isFavorite: Boolean): SearchMetadata

    suspend fun favoriteStatusChange(breweryId: String, isFavorite: Boolean)
}