package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.*
import com.example.twidy.data.entities.FavoriteUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFavoritesRemoteDataSource @Inject constructor(
    private val chatsAPI: ChatsAPI,
    private val dispatcher: CoroutineDispatcher
    ): FavoritesRemoteDataSource {
    override suspend fun fetchFavorites(token: String): Result<List<FavoriteUser>> =
        withContext(dispatcher){
            try {
                val favoriteData = chatsAPI.getFavorite(token)
                if (favoriteData.status == "ok") Success(favoriteData.result.listOf, true) else Failure(favoriteData.message)
            }
            catch (e: Exception) {
                NetworkFailure
            }
        }
}