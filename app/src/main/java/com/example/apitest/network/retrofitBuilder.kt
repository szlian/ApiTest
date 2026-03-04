package com.example.apitest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: DinoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dinoapi.brunosouzadev.com/") // URL base corregida
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DinoApiService::class.java)
    }
}