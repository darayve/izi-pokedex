package com.example.pokedex.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val okHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(RequestInterceptor)
            .build()

    fun client(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

