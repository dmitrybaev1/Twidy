package com.example.twidy.ui.chats.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteItem (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "avatar")
    var avatar: String,
    @ColumnInfo(name = "personName")
    var personName: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "isVideoAccepted")
    var isVideoAccepted: Boolean,
    @ColumnInfo(name = "isAudioAccepted")
    var isAudioAccepted: Boolean
)