package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.database.DataAccessObject
import com.example.newsapp.database.DataBase
import com.example.newsapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyModule {

    @Singleton
    @Provides
    fun providesContextInstance(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesKTORInstance() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Singleton
    @Provides
    fun providesRepositoryInstance(client: HttpClient, dao: DataAccessObject) =
        Repository(client, dao)

    @Singleton
    @Provides
    fun providerDataBaseInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DataBase::class.java,
            "news_database"
        ).build()

    @Singleton
    @Provides
    fun providerDAOInstance(dataBase: DataBase) = dataBase.getDao()
}