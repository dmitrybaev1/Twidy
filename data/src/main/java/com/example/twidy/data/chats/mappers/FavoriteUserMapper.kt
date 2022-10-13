package com.example.twidy.data.chats.mappers

import com.example.twidy.data.response.FavoriteUser
import com.example.twidy.domain.entities.Favorite

class FavoriteUserMapper {

    fun fromFavoriteUserToFavorite(favoriteUsers: List<FavoriteUser>): List<Favorite>{
        val list = arrayListOf<Favorite>()
        for(favoriteUser in favoriteUsers)
            list.add(
                Favorite(
                    favoriteUser.id,
                    favoriteUser.photo,
                    favoriteUser.firstName + " " + favoriteUser.lastName,
                    favoriteUser.biography,
                    favoriteUser.video_call_price?.let { it > 0 } ?: false,
                    favoriteUser.audio_call_price?.let { it > 0 } ?: false
                )
            )
        return list
    }
}