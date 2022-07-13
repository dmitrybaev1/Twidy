package com.example.twidy.domain

import com.example.twidy.data.chats.repositories.AuthRepository
import javax.inject.Inject

class AuthConfirmUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phone: String,id: String,code: String) = authRepository.confirm(phone, id, code)
}