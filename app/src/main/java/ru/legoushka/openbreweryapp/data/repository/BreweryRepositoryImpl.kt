package ru.legoushka.openbreweryapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.legoushka.openbreweryapp.data.converter.BreweryConverter
import ru.legoushka.openbreweryapp.data.converter.SearchMetadataConverter
import ru.legoushka.openbreweryapp.data.source.local.BreweryLocalDataSource
import ru.legoushka.openbreweryapp.data.source.remote.BreweryRemoteDataSource
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.SearchMetadata
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Inject

class BreweryRepositoryImpl @Inject constructor(
    private val breweryRemoteDataSource: BreweryRemoteDataSource,
    private val breweryLocalDataSource: BreweryLocalDataSource,
    private val breweryConverter: BreweryConverter,
    private val searchMetadataConverter: SearchMetadataConverter
) : BreweryRepository {


    override fun observeFavorite(): Flow<List<Brewery>> {
        return breweryLocalDataSource.observeFavorites()
            .map { list -> list.map { model -> breweryConverter.convert(model) } }
    }


    override suspend fun getListPage(page: Int, query: String, isFavorite: Boolean): List<Brewery> =
        when (isFavorite) {
            true -> breweryLocalDataSource.getListPage(page, ITEMS_PER_PAGE, query)
                .map(breweryConverter::convert)
            false -> breweryRemoteDataSource.getListPage(page, ITEMS_PER_PAGE, query)
                .map(breweryConverter::convert)
        }


    override suspend fun getById(id: String): Brewery =
        breweryConverter.convert(
            breweryRemoteDataSource.getById(id)
        )


    override suspend fun getSearchMetadata(
        searchQuery: String,
        isFavorite: Boolean
    ): SearchMetadata {
        if (isFavorite) {
            return searchMetadataConverter.convert(
                breweryLocalDataSource.getSearchMetadata(searchQuery, ITEMS_PER_PAGE)
            )
        } else {
            return searchMetadataConverter.convert(
                breweryRemoteDataSource.getSearchMetadata(searchQuery, ITEMS_PER_PAGE)
            )
        }
    }


    override suspend fun favoriteStatusChange(breweryId: String, isFavorite: Boolean) =
        when (isFavorite) {
            true -> breweryLocalDataSource.deleteBrewery(breweryId)
            else -> breweryLocalDataSource.saveBrewery(breweryRemoteDataSource.getById(breweryId))
        }


    companion object {
        private const val ITEMS_PER_PAGE = 7
    }
}

