package com.example.pokedex.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pokedex.data.network.PokemonAPI
import com.example.pokedex.data.network.RetrofitClient
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding
import com.example.pokedex.data.network.Result
import com.squareup.picasso.Picasso

class PokemonDetailsFragment : Fragment() {
    private lateinit var viewModel: PokemonDetailsViewModel

    private val args: PokemonDetailsFragmentArgs by navArgs()

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding: FragmentPokemonDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this, PokemonDetailsViewModelFactory(
                RetrofitClient.client().create(PokemonAPI::class.java)
            )
        )[PokemonDetailsViewModel::class.java]

        viewModel.fetchPokemonDetails(args.pokemonName.orEmpty())

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon is Result.Success) {
                binding.run {
                    tvPokemonName.text = pokemon.data.name
                    tvPokemonIndex.text = pokemon.data.order.toString()
                    tvPokemonAbility1.text = pokemon.data.abilities[0].ability.name
                    button.setOnClickListener {
                        val action = PokemonDetailsFragmentDirections.actionPokemonDetailsFragmentToPokedexFragment()
                        findNavController().navigate(action)
                    }
                    Picasso.get().load(pokemon.data.sprites.frontDefault).into(ivPokemonImg)
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}