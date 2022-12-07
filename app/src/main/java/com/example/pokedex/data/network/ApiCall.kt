package com.example.pokedex.data.network

import retrofit2.Response
import com.example.pokedex.data.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection

@Suppress("UNCHECKED_CAST")
suspend fun <T> call(apiCall: suspend () -> Response<T>): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            apiCall().let { response ->
                when {
                    response.code() == HttpURLConnection.HTTP_NO_CONTENT -> Result.Success(Unit)
                    response.isSuccessful -> Result.Success(response.body())
                    else -> Result.Error(Throwable(response.message()))
                }
            }
        } catch (exception: Exception) {
            if (exception is HttpException) {
                Result.Error(Throwable(exception.response()!!.message()))
            } else {
                Result.Error(exception)
            }
        } as Result<T>
    }
}
