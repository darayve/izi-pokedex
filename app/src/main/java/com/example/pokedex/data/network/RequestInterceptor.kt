package com.example.pokedex.data.network

import okhttp3.Interceptor
import okhttp3.Response

object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Making request to ${request.url}")
        return chain.proceed(request)
    }
}