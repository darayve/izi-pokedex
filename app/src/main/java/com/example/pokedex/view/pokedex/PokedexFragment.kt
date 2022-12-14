package com.example.pokedex.view.pokedex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.data.network.Result
import com.example.pokedex.data.network.RetrofitClient

class PokedexFragment : Fragment(), PokemonAdapter.PokemonClickListener {
    private lateinit var viewModel: PokemonViewModel

    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!

    private val adapter = PokemonAdapter(this)

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

        viewModel.fetchPokedex()

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.fetchedPokemons.observe(viewLifecycleOwner) { pokemons ->
            if (pokemons is Result.Success) {
                adapter.submitList(pokemons.data)
            }

            if (pokemons is Result.Error) {
                Log.e(
                    PokedexFragment::class.java.canonicalName,
                    pokemons.throwable.message ?: "Error with no message."
                )
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCardClick(pokemonName: String) {
        val action =
            PokedexFragmentDirections.actionPokedexFragmentToPokemonDetailsFragment(pokemonName = pokemonName)
        findNavController().navigate(action)
    }
}