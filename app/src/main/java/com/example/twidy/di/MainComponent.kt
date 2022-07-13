package com.example.twidy.di

import android.content.Context
import com.example.twidy.ui.chats.ChatsViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ChatsModule::class])
interface MainComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AuthComponent
    }
    fun inject(chatsViewModel: ChatsViewModel)
}