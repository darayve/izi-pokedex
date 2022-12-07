package com.example.pokedex.data.network

import com.example.pokedex.data.model.PokemonsResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonAPI {

    @GET("pokemon?limit=10")
    suspend fun getPokemons(): Response<PokemonsResponse>
}