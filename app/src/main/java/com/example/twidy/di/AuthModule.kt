package com.example.twidy.di

import com.example.twidy.data.api.AuthAPI
import com.example.twidy.data.auth.datasources.AuthDataSource
import com.example.twidy.data.auth.datasources.MainAuthDataSource
import com.example.twidy.domain.repositories.AuthRepository
import com.example.twidy.data.auth.repositories.MainAuthRepository
import com.example.twidy.data.RetrofitBuilder
import com.example.twidy.data.auth.mappers.CountryResponseMapper
import com.example.twidy.data.auth.mappers.LocationResponseMapper
import com.example.twidy.data.auth.mappers.TokenResponseMapper

import com.example.twidy.auth.mappers.CountryItemMapper
import com.example.twidy.auth.mappers.LocationItemMapper
import com.example.twidy.auth.mappers.TokenItemMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
abstract class AuthModule {

    @Binds
    abstract fun provideRepository(repository: MainAuthRepository): AuthRepository

    @Binds
    abstract fun provideDataSource(dataSource: MainAuthDataSource): AuthDataSource

    @Provides
    fun provideLocationResponseMapper(): LocationResponseMapper = LocationResponseMapper()

    @Provides
    fun provideCountryResponseMapper(): CountryResponseMapper = CountryResponseMapper()

    @Provides
    fun provideTokenResponseMapper(): TokenResponseMapper = TokenResponseMapper()

    @Provides
    fun provideLocationItemMapper(): LocationItemMapper = LocationItemMapper()

    @Provides
    fun provideCountryItemMapper(): CountryItemMapper = CountryItemMapper()

    @Provides
    fun provideTokenItemMapper(): TokenItemMapper = TokenItemMapper()

    @Provides
    fun provideApi(): AuthAPI = RetrofitBuilder.build().create(AuthAPI::class.java)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}