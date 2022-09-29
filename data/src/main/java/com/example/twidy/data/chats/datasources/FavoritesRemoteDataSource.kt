package com.example.twidy.data.chats.datasources

import com.example.twidy.dom.entities.Favorite

interface FavoritesRemoteDataSource {
    suspend fun fetchFavorites(token: String): Result<List<Favorite>>
}