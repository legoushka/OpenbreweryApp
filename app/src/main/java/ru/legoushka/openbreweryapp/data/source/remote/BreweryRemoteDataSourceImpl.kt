package ru.legoushka.openbreweryapp.data.source.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.data.model.SearchMetadataModel
import javax.inject.Inject

class BreweryRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BreweryRemoteDataSource {

    override suspend fun getListPage(
        page: Int,
        itemsPerPage: Int,
        query: String
    ): List<BreweryModel> =
        withContext(coroutineDispatcher) {

            return@withContext httpClient.get(BASE_URL) {
                url {
                    if (query.isNotEmpty()) {
                        parameters.append("by_name", query)
                    }
                    parameters.append("page", "$page")
                    parameters.append("per_page", "$itemsPerPage")
                }
            }.body<List<BreweryModel>>()


        }

    override suspend fun getById(id: String): BreweryModel =
        withContext(coroutineDispatcher) {
            return@withContext httpClient.get(BASE_URL) {
                url {
                    appendPathSegments(id)
                }
            }.body<BreweryModel>()
        }


    override suspend fun getSearchMetadata(searchQuery: String, itemsPerPage: Int): SearchMetadataModel =
        withContext(coroutineDispatcher) {
            return@withContext httpClient.get(BASE_URL) {
                url {
                    appendPathSegments("meta")
                    parameters.append("by_name", searchQuery)
                    parameters.append("per_page", "$itemsPerPage")
                }
            }.body<SearchMetadataModel>()
        }

    companion object {
        private const val BASE_URL = "https://api.openbrewerydb.org/breweries"
    }
}