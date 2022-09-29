package com.example.twidy.data.auth.mappers

import com.example.twidy.data.response.ResultConfirmData
import com.example.twidy.domain.entities.auth.Token

class TokenResponseMapper {

    fun fromResultConfirmDataToToken(resultConfirmData: ResultConfirmData): Token =
        Token(
            resultConfirmData.access_token,
            resultConfirmData.call_id,
            resultConfirmData.created,
            resultConfirmData.expires
        )
}