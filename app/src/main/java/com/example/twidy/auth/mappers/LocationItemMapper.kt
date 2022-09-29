package com.example.twidy.auth.mappers

import com.example.twidy.domain.entities.auth.Location
import com.example.twidy.auth.entities.LocationItem

class LocationItemMapper {

    fun fromLocationToLocationItem(location: Location): LocationItem =
        LocationItem(
            location.name,
            location.phonecode
        )

}