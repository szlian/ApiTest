package com.example.apitest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitBuilder {
    fun build() : Retrofit =
        Retrofit.Builder()
            .baseUrl("https://perenual.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
