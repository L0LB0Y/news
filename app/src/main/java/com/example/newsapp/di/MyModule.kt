package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.model.local.ArticlesDAO
import com.example.newsapp.model.local.ArticlesDataBase
import com.example.newsapp.model.remote.NewsAPI
import com.example.newsapp.repository.Repository
import com.example.newsapp.view_model.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyModule {

    @Singleton
    @Provides
    fun providesContextInstance(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesRepositoryInstance(newsAPI: NewsAPI, dao: ArticlesDAO): Repository {
        return Repository(newsAPI, dao)
    }

    @Singleton
    @Provides
    fun providesViewModelInstance(
        repository: Repository,
        @ApplicationContext context: Context
    ): HomeViewModel {
        return HomeViewModel(repository, context)
    }

    @Singleton
    @Provides
    fun providerDataBaseInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ArticlesDataBase::class.java,
            "news_database"
        ).build()

    @Singleton
    @Provides
    fun providerDAOInstance(articlesDataBase: ArticlesDataBase) = articlesDataBase.getDao()

    @Singleton
    @Provides
    fun providerRetrofitInstance(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPI::class.java)
    }
}