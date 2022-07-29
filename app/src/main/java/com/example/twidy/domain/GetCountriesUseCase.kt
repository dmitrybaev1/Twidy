package com.example.twidy.domain

import com.example.twidy.data.auth.repositories.AuthRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.fetchCountries()
}