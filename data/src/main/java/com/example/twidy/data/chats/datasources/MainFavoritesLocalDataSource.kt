package com.example.twidy.data.chats.datasources

import com.example.twidy.data.chats.mappers.FavoriteEntityMapper
import com.example.twidy.data.database.AppDatabase
import com.example.twidy.dom.entities.Favorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFavoritesLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val favoriteEntityMapper: FavoriteEntityMapper
    ): FavoritesLocalDataSource {
    override suspend fun fetchFavorites(): List<Favorite> =
        withContext(dispatcher){
            favoriteEntityMapper.fromFavoriteEntityToFavorite(database.favoriteDao().getAll())
        }
    override suspend fun saveFavorites(favorites: List<Favorite>) =
        withContext(dispatcher){
            database.favoriteDao().insertAll(favoriteEntityMapper.fromFavoriteToFavoriteEntity(favorites))
        }
}