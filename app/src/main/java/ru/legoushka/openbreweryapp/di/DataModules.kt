package ru.legoushka.openbreweryapp.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import ru.legoushka.openbreweryapp.data.repository.BreweryRepositoryImpl
import ru.legoushka.openbreweryapp.data.source.local.BreweryDatabase
import ru.legoushka.openbreweryapp.data.source.local.BreweryLocalDataSource
import ru.legoushka.openbreweryapp.data.source.local.BreweryLocalDataSourceImpl
import ru.legoushka.openbreweryapp.data.source.remote.BreweryRemoteDataSource
import ru.legoushka.openbreweryapp.data.source.remote.BreweryRemoteDataSourceImpl
import ru.legoushka.openbreweryapp.domain.repository.BreweryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    private const val REMOTE_SOURCE_REQUEST_TIMEOUT = 10000L

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REMOTE_SOURCE_REQUEST_TIMEOUT
                connectTimeoutMillis = REMOTE_SOURCE_REQUEST_TIMEOUT
                socketTimeoutMillis = REMOTE_SOURCE_REQUEST_TIMEOUT
            }
            install(ContentNegotiation) {
                json()
            }
        }



    }

    @Singleton
    @Provides
    fun provideBreweryRemoteDataSource(httpClient: HttpClient): BreweryRemoteDataSource =
        BreweryRemoteDataSourceImpl(httpClient)

    @Singleton
    @Provides
    fun provideBreweryLocalDataSource(database: BreweryDatabase): BreweryLocalDataSource =
        BreweryLocalDataSourceImpl(database)


}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindBreweryRepository(impl: BreweryRepositoryImpl): BreweryRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): BreweryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BreweryDatabase::class.java,
            "Favorite.db"
        ).build()
    }
}

