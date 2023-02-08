package ru.legoushka.openbreweryapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.legoushka.openbreweryapp.data.model.BreweryModel
import ru.legoushka.openbreweryapp.data.source.local.BreweryDao

@Database(entities = [BreweryModel::class], version = 1, exportSchema = false)
abstract class BreweryDatabase : RoomDatabase() {
    abstract fun breweryDao(): BreweryDao
}