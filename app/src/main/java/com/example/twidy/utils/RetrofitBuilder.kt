package com.example.twidy.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    fun build(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://d3v.twidy.ru/api/methods/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}