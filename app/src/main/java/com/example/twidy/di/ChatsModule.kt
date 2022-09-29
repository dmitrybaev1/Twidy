package com.example.twidy.di

import android.content.Context
import com.example.twidy.data.api.ChatsAPI
import com.example.twidy.dat.chats.datasources.*
import com.example.twidy.data.chats.repositories.MainChatsRepository
import com.example.twidy.data.chats.repositories.MainFavoritesRepository
import com.example.twidy.data.database.AppDatabase

import com.example.twidy.data.RetrofitBuilder
import com.example.twidy.data.chats.mappers.ChatEntityMapper
import com.example.twidy.data.chats.mappers.ChatResponseMapper
import com.example.twidy.data.chats.mappers.FavoriteEntityMapper
import com.example.twidy.data.chats.datasources.*
import com.example.twidy.data.chats.mappers.FavoriteUserMapper
import com.example.twidy.domain.repositories.ChatsRepository
import com.example.twidy.domain.repositories.FavoritesRepository
import com.example.twidy.chats.mappers.ChatItemMapper
import com.example.twidy.chats.mappers.FavoriteItemMapper
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

    @Provides
    fun provideChatItemMapper(): ChatItemMapper = ChatItemMapper()

    @Provides
    fun provideFavoriteItemMapper(): FavoriteItemMapper = FavoriteItemMapper()

    @Provides
    fun provideChatEntityMapper(): ChatEntityMapper = ChatEntityMapper()

    @Provides
    fun provideChatResponseMapper(): ChatResponseMapper = ChatResponseMapper()

    @Provides
    fun provideFavoriteEntityMapper(): FavoriteEntityMapper = FavoriteEntityMapper()

    @Provides
    fun provideFavoriteUserMapper(): FavoriteUserMapper = FavoriteUserMapper()

    @Binds
    abstract fun provideInternetChecker(internetChecker: DefaultInternetChecker): InternetChecker

}