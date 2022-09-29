package com.example.twidy.data.response

data class LocationData (
    val status: String,
    val result: ResultLocationData,
    val code: Int,
    val message: String
)