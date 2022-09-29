package com.example.twidy.data.response

data class Linked (
    val net_id: Int,
    val net_name: String,
    val net_fa_icon: String,
    val net_hex_color: String,
    val username: String,
    val photo: String,
    val followers_count: Int
)