package com.example.twidy.auth.mappers

import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.auth.entities.CountryItem

class CountryItemMapper {

    fun fromCountryToCountryItem(countries: List<Country>): List<CountryItem>{
        val list = arrayListOf<CountryItem>()
        for(c in countries)
            list.add(
                CountryItem(
                    c.name,
                    c.phonecode,
                    c.sortname
                )
            )
        return list
    }
}