package com.example.twidy.domain.usecases

import com.example.twidy.data.auth.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phone: String) = authRepository.auth(phone)
}