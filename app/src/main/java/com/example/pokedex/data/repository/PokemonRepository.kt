package com.example.pokedex.data.repository

import com.example.pokedex.data.model.PokemonsResponse
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.network.call

class PokemonRepository (private val pokemonAPI: PokemonAPI) {

    suspend fun getPokemons(): Result<PokemonsResponse> {
        return when (val result = call { pokemonAPI.getPokemons() }) {
            is Result.Success -> Result.Success(result.data)
            is Result.Error -> Result.Error(result.throwable)
            else -> Result.Error(Throwable("UNKNOWN"))
        }
    }

}