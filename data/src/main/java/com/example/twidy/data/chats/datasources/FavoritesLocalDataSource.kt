package com.example.twidy.data.chats.datasources

import com.example.twidy.domain.entities.Favorite

interface FavoritesLocalDataSource {

    suspend fun fetchFavorites(): List<Favorite>

    suspend fun saveFavorites(favorites: List<Favorite>)
}