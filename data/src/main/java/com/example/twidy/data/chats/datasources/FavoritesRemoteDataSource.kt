package com.example.twidy.data.chats.datasources

import com.example.twidy.domain.entities.Favorite
import com.example.twidy.domain.Result

interface FavoritesRemoteDataSource {
    suspend fun fetchFavorites(token: String): Result<List<Favorite>>
}