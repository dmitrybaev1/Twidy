package com.example.twidy.domain.usecases

import com.example.twidy.data.auth.repositories.AuthRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.fetchLocation()
}