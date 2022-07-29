package com.example.twidy.data.chats.datasources

import com.example.twidy.data.chats.entities.FavoriteItem

interface FavoritesLocalDataSource {

    suspend fun fetchFavorites(): List<FavoriteItem>

    suspend fun saveFavorites(favorites: List<FavoriteItem>)
}