package com.example.twidy.data.response

data class Linked (
    var net_id: Int,
    var net_name: String,
    var net_fa_icon: String,
    var net_hex_color: String,
    var username: String,
    var photo: String,
    var followers_count: Int
)