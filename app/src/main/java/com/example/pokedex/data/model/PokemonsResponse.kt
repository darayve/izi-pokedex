package com.example.pokedex.data.model

data class PokemonsResponse(
    val count: Long,
    val next: String,
    val previous: String,
    val results: List<PokemonInfos>
)