package com.example.twidy.ui.di

import android.content.Context
import com.example.twidy.ui.chats.ChatsViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ChatsModule::class])
interface MainComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): MainComponent
    }
    fun inject(chatsViewModel: ChatsViewModel)
}