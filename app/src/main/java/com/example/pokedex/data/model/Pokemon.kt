package com.example.pokedex.data.model

data class Pokemon(
    val order: Long,
    val name: String,
    val types: List<Type>
)

data class Type(
    val type: PokemonType,
    val slot: Int
)

data class PokemonType(
    val name: String,
    val url: String
)
