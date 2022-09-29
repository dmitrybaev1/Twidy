package com.example.twidy.data.response

data class StaticData(
    val status: String,
    val result: ResultStaticData,
    val code: Int,
    val message: String
)