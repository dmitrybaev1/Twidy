package com.example.twidy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private var count = 0;
    val next = MutableLiveData<Int>()
    fun next(){
        count++
        next.value=count
    }
    fun skip(){
        count=5
        next.value=count
        //
    }
}