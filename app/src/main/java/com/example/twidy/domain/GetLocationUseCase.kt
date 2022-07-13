package com.example.twidy.domain

import com.example.twidy.data.chats.repositories.AuthRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.fetchLocation()
}