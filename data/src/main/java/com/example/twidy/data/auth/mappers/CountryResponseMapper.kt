package com.example.twidy.data.auth.mappers

import com.example.twidy.data.response.CountryResponse
import com.example.twidy.domain.entities.auth.Country

class CountryResponseMapper {

    fun fromCountryResponseToCountry(countryResponse: List<CountryResponse>): List<Country>{
        val list = arrayListOf<Country>()
        for(c in countryResponse)
            list.add(
                Country(
                    c.name,
                    c.phonecode,
                    c.sortname
                )
            )
        return list
    }
}