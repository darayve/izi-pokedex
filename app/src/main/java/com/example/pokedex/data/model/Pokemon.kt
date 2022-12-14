package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonInfos(
    val name: String,
    val url: String
)

data class Pokemon(
    val order: Long,
    val name: String,
    val types: List<Type>,
    val sprites: Sprites,
    val abilities: List<AbilityInfo>
)

data class AbilityInfo(
    val ability: Ability,
    @SerializedName("is_hidden") val isHidden: Boolean,
    val slot: Long
) : Serializable

data class Ability(
    val name: String,
    val url: String
)

data class Sprites(
    @SerializedName("back_default") val backDefault: String,
    @SerializedName("back_shiny") val backShiny: String,
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("front_shiny") val frontShiny: String
) : Serializable

data class Type(
    val type: PokemonType,
    val slot: Int
)

data class PokemonType(
    val name: String,
    val url: String
)
