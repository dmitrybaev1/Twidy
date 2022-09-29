package com.example.twidy.data.chats.mappers

import com.example.twidy.data.chats.entities.FavoriteEntity
import com.example.twidy.dom.entities.Favorite

class FavoriteEntityMapper {

    fun fromFavoriteToFavoriteEntity(favorites: List<Favorite>): List<FavoriteEntity> {
        val list = arrayListOf<FavoriteEntity>()
        for(favorite in favorites)
            list.add(
                FavoriteEntity(
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

    fun fromFavoriteEntityToFavorite(favoriteEntities: List<FavoriteEntity>): List<Favorite>{
        val list = arrayListOf<Favorite>()
        for(favoriteItem in favoriteEntities)
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