package com.example.twidy.di

import android.content.Context
import com.example.twidy.data.api.ChatsAPI
import com.example.twidy.data.chats.datasources.*
import com.example.twidy.data.chats.repositories.ChatsRepository
import com.example.twidy.data.chats.repositories.FavoritesRepository
import com.example.twidy.data.chats.repositories.MainChatsRepository
import com.example.twidy.data.chats.repositories.MainFavoritesRepository
import com.example.twidy.data.database.AppDatabase
import com.example.twidy.utils.DefaultInternetChecker
import com.example.twidy.utils.InternetChecker
import com.example.twidy.utils.RetrofitBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
abstract class ChatsModule {
    @Binds
    abstract fun provideChatsRepository(repository: MainChatsRepository): ChatsRepository

    @Binds
    abstract fun provideFavoritesRepository(repository: MainFavoritesRepository): FavoritesRepository

    @Binds
    abstract fun provideChatsLocalDataSource(dataSource: MainChatsLocalDataSource): ChatsLocalDataSource

    @Binds
    abstract fun provideChatsRemoteDataSource(dataSource: MainChatsRemoteDataSource): ChatsRemoteDataSource

    @Binds
    abstract fun provideFavoritesLocalDataSource(dataSource: MainFavoritesLocalDataSource): FavoritesLocalDataSource

    @Binds
    abstract fun provideChatsLocalDataSource(dataSource: MainFavoritesRemoteDataSource): FavoritesRemoteDataSource

    @Provides
    fun provideChatsApi(): ChatsAPI = RetrofitBuilder.build().create(ChatsAPI::class.java)

    @Provides
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Binds
    abstract fun provideInternetChecker(internetChecker: DefaultInternetChecker): InternetChecker

}