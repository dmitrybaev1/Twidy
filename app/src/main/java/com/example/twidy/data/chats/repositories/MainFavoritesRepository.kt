package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Failure
import com.example.twidy.data.api.NetworkFailure
import com.example.twidy.data.api.Result
import com.example.twidy.data.api.Success
import com.example.twidy.data.chats.datasources.FavoritesLocalDataSource
import com.example.twidy.data.chats.datasources.FavoritesRemoteDataSource
import com.example.twidy.data.entities.FavoriteUser
import com.example.twidy.mappers.FavoriteMapper
import com.example.twidy.ui.chats.items.FavoriteItem
import com.example.twidy.utils.InternetChecker
import javax.inject.Inject

class MainFavoritesRepository @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val favoritesRemoteDataSource: FavoritesRemoteDataSource,
    private val internetChecker: InternetChecker
    ): FavoritesRepository {
    override suspend fun fetchFavorites(token: String): Result<List<FavoriteItem>> {
        return if(internetChecker.isOnline){
            when(val result = favoritesRemoteDataSource.fetchFavorites(token)){
                is Success<List<FavoriteUser>> -> {
                    val favorites = mapFavorites(result.data)
                    favoritesLocalDataSource.saveFavorites(favorites)
                    Success(favorites,result.isRemote)
                }
                is Failure -> result
                is NetworkFailure -> result
            }
        } else{
            val favorites = favoritesLocalDataSource.fetchFavorites()
            Success(favorites, false)
        }
    }
    private fun mapFavorites(items: List<FavoriteUser>): List<FavoriteItem>{
        val favorites = arrayListOf<FavoriteItem>()
        for(i in items)
            favorites.add(FavoriteMapper.favoriteUserToFavoriteItem(i))
        return favorites
    }
}