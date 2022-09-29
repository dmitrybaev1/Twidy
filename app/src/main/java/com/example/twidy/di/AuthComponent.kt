package com.example.twidy.di

import com.example.twidy.auth.AuthViewModel
import dagger.Component

@Component(modules = [AuthModule::class])
interface AuthComponent {
    @Component.Factory
    interface Factory{
        fun create(): AuthComponent
    }
    fun inject(viewModel: AuthViewModel)
}