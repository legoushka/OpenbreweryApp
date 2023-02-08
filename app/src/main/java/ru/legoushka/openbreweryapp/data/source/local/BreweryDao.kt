package ru.legoushka.openbreweryapp.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.legoushka.openbreweryapp.data.model.BreweryModel

@Dao
interface BreweryDao {

    @Query("SELECT * FROM Favorite")
    fun observeFavoriteList(): Flow<List<BreweryModel>>

    @Query("SELECT * FROM Favorite")
    suspend fun getFavoriteList(): List<BreweryModel>

    @Query("SELECT * FROM Favorite WHERE id = :id")
    suspend fun getFavoriteById(id: String): BreweryModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBreweryToFavorite(breweryModel: BreweryModel)

    @Delete
    suspend fun deleteBreweryFromFavorite(breweryModel: BreweryModel)
}