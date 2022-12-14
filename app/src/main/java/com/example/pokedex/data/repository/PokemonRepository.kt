package com.example.pokedex.data.repository

import android.util.Log
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonsResponse
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.network.call

class PokemonRepository(private val pokemonAPI: PokemonAPI) {

    suspend fun getPokedex() : Result<List<Pokemon>> {
        val fetchedPokemons = mutableListOf<Pokemon>()

        val pokemons = call { pokemonAPI.getPokemons() }

        if (pokemons is Result.Error) {
            return Result.Error(pokemons.throwable)
        }

        if (pokemons is Result.Success) {
            pokemons.data.results.map { pokemon ->
                val pokeId = pokemon.url.split("/")[6].toLong()

                val infos = call { pokemonAPI.getPokemonInfos(pokemonId = pokeId) }

                if (infos is Result.Error) {
                    return Result.Error(infos.throwable)
                }

                if (infos is Result.Success) {
                    fetchedPokemons.add(element = infos.data)
                }
            }
        }

        return Result.Success(fetchedPokemons)
    }
}