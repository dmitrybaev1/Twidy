package com.example.twidy.data.response

data class PopularUser (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val domain: String,
    val photo: String,
    val price: Int?,
    val biography: String,
    val is_online: Boolean,
    val is_verify: Boolean
)