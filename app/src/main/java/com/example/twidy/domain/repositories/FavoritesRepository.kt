package com.example.twidy.domain.repositories

import com.example.twidy.domain.Result
import com.example.twidy.domain.entities.Favorite

interface FavoritesRepository {

    suspend fun fetchFavorites(token: String): Result<List<Favorite>>

}