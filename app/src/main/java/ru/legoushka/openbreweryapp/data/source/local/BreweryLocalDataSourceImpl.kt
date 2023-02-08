package ru.legoushka.openbreweryapp.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.data.model.SearchMetadataModel
import kotlin.math.min

class BreweryLocalDataSourceImpl(
    private val database: BreweryDatabase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BreweryLocalDataSource {

    override fun observeFavorites(): Flow<List<BreweryModel>> {
        return database.breweryDao().observeFavoriteList()
    }


    override suspend fun getListPage(page: Int, perPage: Int, searchQuery: String): List<BreweryModel> =
        withContext(coroutineDispatcher) {
            val list = database.breweryDao().getFavoriteList()
            if (searchQuery != "") {
                val sortedList = list.filter { it.name.lowercase().contains(searchQuery.lowercase()) }

                return@withContext sortedList.subList((page-1) * perPage, min(page*perPage, sortedList.size))
            } else {
                return@withContext list.subList((page-1) * perPage, min(page*perPage, list.size))
            }
        }

    override suspend fun saveBrewery(breweryModel: BreweryModel) =
        withContext(coroutineDispatcher) {
            database.breweryDao().saveBreweryToFavorite(breweryModel)
        }

    override suspend fun getSearchMetadata(
        searchQuery: String,
        itemsPerPage: Int
    ): SearchMetadataModel = withContext(coroutineDispatcher) {
        if (searchQuery != "") {
            return@withContext SearchMetadataModel(
                "",
                itemsPerPage.toString(),
                database.breweryDao().getFavoriteList().count { it.name.contains(searchQuery) }
                    .toString()
            )
        } else return@withContext SearchMetadataModel(
            "",
            itemsPerPage.toString(),
            database.breweryDao().getFavoriteList().count().toString()
        )
    }

    override suspend fun deleteBrewery(breweryId: String): Unit = withContext(coroutineDispatcher) {
        database.breweryDao().getFavoriteById(breweryId)?.let {
            database.breweryDao().deleteBreweryFromFavorite(it)
        }
    }
}