package com.example.twidy.data.chats.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "personName")
    val personName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "isVideoAccepted")
    val isVideoAccepted: Boolean,
    @ColumnInfo(name = "isAudioAccepted")
    val isAudioAccepted: Boolean
)
