package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Result
import com.example.twidy.data.chats.entities.FavoriteItem

interface FavoritesRepository {

    suspend fun fetchFavorites(token: String): Result<List<FavoriteItem>>

}