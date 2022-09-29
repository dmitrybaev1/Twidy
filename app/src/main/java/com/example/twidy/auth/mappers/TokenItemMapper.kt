package com.example.twidy.auth.mappers

import com.example.twidy.auth.entities.TokenItem
import com.example.twidy.domain.entities.auth.Token

class TokenItemMapper {

    fun fromTokenToTokenItem(token: Token): TokenItem =
        TokenItem(
            token.access_token,
            token.call_id,
            token.created,
            token.expires
        )
}