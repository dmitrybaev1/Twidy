package com.example.twidy.data.chats.repositories

import com.example.twidy.data.InternetChecker
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.data.chats.datasources.FavoritesLocalDataSource
import com.example.twidy.data.chats.datasources.FavoritesRemoteDataSource
import com.example.twidy.domain.entities.Favorite
import com.example.twidy.domain.repositories.FavoritesRepository
import javax.inject.Inject
import com.example.twidy.domain.Result

class MainFavoritesRepository @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val favoritesRemoteDataSource: FavoritesRemoteDataSource,
    private val internetChecker: InternetChecker
    ): FavoritesRepository {
    override suspend fun fetchFavorites(token: String): Result<List<Favorite>> {
        return if(internetChecker.isOnline){
            when(val result = favoritesRemoteDataSource.fetchFavorites(token)){
                is Success<List<Favorite>> -> {
                    val favorites = result.data
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

}