package com.example.twidy.data.response

data class PopularUser (
    var id: Int,
    var firstName: String,
    var lastName: String,
    var domain: String,
    var photo: String,
    var price: Int?,
    var biography: String,
    var is_online: Boolean,
    var is_verify: Boolean
)