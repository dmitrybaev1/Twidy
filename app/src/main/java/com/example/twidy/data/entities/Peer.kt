package com.example.twidy.data.entities

data class Peer (
    var id: Int,
    var type: String?,
    var name: String,
    var image: String?,
    var is_online: Int?
)