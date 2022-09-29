package com.example.twidy.data.auth.mappers

import com.example.twidy.data.response.LocationResponse
import com.example.twidy.domain.entities.auth.Location

class LocationResponseMapper {

    fun fromLocationResponseToLocation(locationResponse: LocationResponse): Location =
        Location(
            locationResponse.name,
            locationResponse.phonecode
        )
}