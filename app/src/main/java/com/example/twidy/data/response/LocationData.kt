package com.example.twidy.data.response

data class LocationData (
    var status: String,
    var result: ResultLocationData,
    var code: Int,
    var message: String
)