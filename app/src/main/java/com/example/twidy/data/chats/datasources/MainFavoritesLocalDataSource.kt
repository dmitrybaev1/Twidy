package com.example.twidy.data.chats.datasources

import com.example.twidy.data.database.AppDatabase
import com.example.twidy.ui.chats.items.FavoriteItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFavoritesLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher
    ): FavoritesLocalDataSource {
    override suspend fun fetchFavorites(): List<FavoriteItem> =
        withContext(dispatcher){
            database.favoriteItemDao().getAll()
        }
    override suspend fun saveFavorites(favorites: List<FavoriteItem>) =
        withContext(dispatcher){
            database.favoriteItemDao().insertAll(favorites)
        }
}