package com.example.twidy.di

import com.example.twidy.data.api.AuthAPI
import com.example.twidy.data.chats.datasources.AuthDataSource
import com.example.twidy.data.chats.datasources.MainAuthDataSource
import com.example.twidy.data.chats.repositories.AuthRepository
import com.example.twidy.data.chats.repositories.MainAuthRepository
import com.example.twidy.utils.RetrofitBuilder
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
    fun provideApi(): AuthAPI = RetrofitBuilder.build().create(AuthAPI::class.java)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}