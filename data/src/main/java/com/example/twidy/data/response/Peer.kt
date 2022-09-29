package com.example.twidy.data.response

data class Peer (
    val id: Int,
    val type: String?,
    val name: String,
    val image: String?,
    val is_online: Int?
)