package com.example.twidy.data.response

data class FavoriteUser (
    var id: Int,
    var domain: String,
    var last_activity: Int,
    var is_online: Boolean,
    var linked: List<Linked>,
    var totalUserFollowers: Int,
    var is_followed: Int,
    var is_self: Boolean,
    var dialog: Dialog,
    //var services
    var firstName: String,
    var video_call_price: Int?,
    var audio_call_price: Int?,
    var lastName: String,
    var biography: String,
    var photo: String,
    //var industry: Industry,
    var price: Int?
)