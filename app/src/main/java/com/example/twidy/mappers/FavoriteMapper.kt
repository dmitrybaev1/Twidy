package com.example.twidy.mappers

import com.example.twidy.data.entities.FavoriteUser
import com.example.twidy.ui.chats.items.FavoriteItem

object FavoriteMapper {
    fun favoriteUserToFavoriteItem(favoriteUser: FavoriteUser): FavoriteItem = FavoriteItem(
        favoriteUser.id,
        favoriteUser.photo,
        favoriteUser.firstName + " " + favoriteUser.lastName,
        favoriteUser.biography,
        favoriteUser.video_call_price?.let { it > 0 } ?: false,
        favoriteUser.audio_call_price?.let { it > 0 } ?: false
    )
}