package com.example.twidy.ui.chats.mappers

import com.example.twidy.domain.entities.Favorite
import com.example.twidy.ui.chats.entities.FavoriteItem

class FavoriteItemMapper {

    fun fromFavoriteToFavoriteItem(favorites: List<Favorite>): List<FavoriteItem>{
        val list = arrayListOf<FavoriteItem>()
        for(favorite in favorites)
            list.add(
                FavoriteItem(
                    favorite.id,
                    favorite.avatar,
                    favorite.personName,
                    favorite.description,
                    favorite.isAudioAccepted,
                    favorite.isVideoAccepted
                )
            )
        return list
    }

    fun fromFavoriteItemToFavorite(favoriteItems: List<FavoriteItem>): List<Favorite> {
        val list = arrayListOf<Favorite>()
        for(favoriteItem in favoriteItems)
            list.add(
                Favorite(
                    favoriteItem.id,
                    favoriteItem.avatar,
                    favoriteItem.personName,
                    favoriteItem.description,
                    favoriteItem.isAudioAccepted,
                    favoriteItem.isVideoAccepted
                )
            )
        return list
    }
}