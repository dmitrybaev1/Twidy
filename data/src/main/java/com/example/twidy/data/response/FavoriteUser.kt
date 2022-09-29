package com.example.twidy.data.response

data class FavoriteUser (
    val id: Int,
    val domain: String,
    val last_activity: Int,
    val is_online: Boolean,
    val linked: List<Linked>,
    val totalUserFollowers: Int,
    val is_followed: Int,
    val is_self: Boolean,
    val dialog: Dialog,
    //var services
    val firstName: String,
    val video_call_price: Int?,
    val audio_call_price: Int?,
    val lastName: String,
    val biography: String,
    val photo: String,
    //var industry: Industry,
    val price: Int?
)