package com.example.twidy.data.chats.datasources

import com.example.twidy.dat.api.*
import com.example.twidy.data.api.ChatsAPI
import com.example.twidy.data.chats.mappers.FavoriteUserMapper
import com.example.twidy.dom.Failure
import com.example.twidy.dom.NetworkFailure
import com.example.twidy.dom.Success
import com.example.twidy.dom.entities.Favorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFavoritesRemoteDataSource @Inject constructor(
    private val chatsAPI: ChatsAPI,
    private val dispatcher: CoroutineDispatcher,
    private val favoriteUserMapper: FavoriteUserMapper
    ): FavoritesRemoteDataSource {
    override suspend fun fetchFavorites(token: String): Result<List<Favorite>> =
        withContext(dispatcher){
            try {
                val favoriteData = chatsAPI.getFavorite(token)
                if (favoriteData.status == "ok") Success(
                    favoriteUserMapper.fromFavoriteUserToFavorite(favoriteData.result.listOf),
                    true
                )
                else
                    Failure(favoriteData.message)
            }
            catch (e: Exception) {
                NetworkFailure
            }
        }
}