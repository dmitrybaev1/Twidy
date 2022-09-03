package com.example.twidy.domain.entities

data class Favorite(
    val id: Int,
    val avatar: String,
    val personName: String,
    val description: String,
    val isVideoAccepted: Boolean,
    val isAudioAccepted: Boolean
)
