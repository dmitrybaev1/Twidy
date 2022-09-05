package com.example.twidy.domain.usecases

import com.example.twidy.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend operator fun invoke(token: String) = favoritesRepository.fetchFavorites(token)
}