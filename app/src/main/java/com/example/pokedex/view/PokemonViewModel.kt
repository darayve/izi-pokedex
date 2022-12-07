package com.example.pokedex.view

import androidx.lifecycle.*
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonsResponse
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemons = MutableLiveData<Result<PokemonsResponse>>()
    val pokemons: LiveData<Result<PokemonsResponse>> = _pokemons

    fun getPokemons() {
        _pokemons.value = Result.Loading
        viewModelScope.launch {
            _pokemons.value = repository.getPokemons()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val pokemonAPI: PokemonAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(PokemonRepository(pokemonAPI)) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}