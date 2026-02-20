package com.example.apitest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitBuilder {
    fun build() : Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dinoapi.brunosouzadev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
