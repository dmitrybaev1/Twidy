package com.example.twidy.ui.chats.entities

data class FavoriteItem (
    val id: Int,
    val avatar: String,
    val personName: String,
    val description: String,
    val isVideoAccepted: Boolean,
    val isAudioAccepted: Boolean
)