package com.example.twidy.data.response

data class StaticData(
    var status: String,
    var result: ResultStaticData,
    var code: Int,
    var message: String
)