package com.example.pokedex.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.network.RetrofitClient
import retrofit2.Retrofit
import retrofit2.create

class PokedexFragment : Fragment() {
    private lateinit var viewModel: PokemonViewModel

    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!

    private val adapter = PokemonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokedexBinding.inflate(inflater, container, false).apply {
            rvPokemons.adapter = adapter
        }

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                RetrofitClient.client().create(PokemonAPI::class.java)
            )
        )[PokemonViewModel::class.java]

        viewModel.getPokemons()

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            if (pokemons is Result.Success) {
                adapter.submitList(pokemons.data.results)
            }

            if (pokemons is Result.Error) {
                Log.i("mytag", pokemons.throwable.message ?: "Error with no message.")
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}