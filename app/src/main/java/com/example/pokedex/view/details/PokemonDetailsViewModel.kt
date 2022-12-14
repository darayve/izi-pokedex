package com.example.pokedex.view.details

import androidx.lifecycle.*
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.repository.PokemonRepository
import com.example.pokedex.view.pokedex.PokemonViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonDetailsViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonDetails = MutableLiveData<Result<Pokemon>>()
    val pokemonDetails: LiveData<Result<Pokemon>> = _pokemonDetails

    fun fetchPokemonDetails(pokemonName: String) {
        _pokemonDetails.value = Result.Loading
        viewModelScope.launch {
            _pokemonDetails.value = repository.getPokemonDetails(pokemonName = pokemonName)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PokemonDetailsViewModelFactory(private val pokemonAPI: PokemonAPI) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
            return PokemonDetailsViewModel(PokemonRepository(pokemonAPI)) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}
