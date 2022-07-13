package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.Result
import com.example.twidy.data.entities.FavoriteUser

interface FavoritesRemoteDataSource {
    suspend fun fetchFavorites(token: String): Result<List<FavoriteUser>>
}