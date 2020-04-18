package com.example.twidy.entities

import com.example.twidy.entities.Category
import com.example.twidy.entities.Country
import com.example.twidy.entities.Industry
import com.example.twidy.entities.Networks

data class ResultStaticData (
    var country: ArrayList<Country>,
    var networks: ArrayList<Networks>,
    var industry: ArrayList<Industry>,
    var category: ArrayList<Category>
)