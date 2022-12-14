package com.example.pokedex.view.pokedex

import androidx.lifecycle.*
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonsResponse
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _fetchedPokemons = MutableLiveData<Result<List<Pokemon>>>()
    val fetchedPokemons: LiveData<Result<List<Pokemon>>> = _fetchedPokemons

    fun fetchPokedex() {
        _fetchedPokemons.value = Result.Loading
        viewModelScope.launch {
            _fetchedPokemons.value = repository.getPokedex()
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