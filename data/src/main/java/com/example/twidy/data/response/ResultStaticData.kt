package com.example.twidy.data.response

data class ResultStaticData (
    val countries: List<CountryResponse>,
    val networks: List<Networks>,
    val industries: List<Industry>,
    val categories: List<Category>
)