package com.example.pokedex.data.network

import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    @GET("pokemon?limit=10")
    suspend fun getPokemons(): Response<PokemonsResponse>

    @GET("pokemon/{pokemon_id}")
    suspend fun getPokemonInfos(
        @Path("pokemon_id") pokemonId: Long
    ): Response<Pokemon>

    @GET("pokemon/{pokemon_name}")
    suspend fun getPokemonInfosByName(
        @Path("pokemon_name") pokemonName: String
    ): Response<Pokemon>
}