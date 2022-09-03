package com.example.twidy.data.response

data class ResultStaticData (
    var countries: List<Country>,
    var networks: List<Networks>,
    var industries: List<Industry>,
    var categories: List<Category>
)