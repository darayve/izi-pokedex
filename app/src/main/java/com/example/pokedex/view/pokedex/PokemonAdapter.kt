package com.example.pokedex.view.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val pokemonClickListener: PokemonClickListener
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)

        holder.binding.run {
            this.pokemon = pokemon
            tvPokemonName.text.toString().uppercase()
            tvPokemonFirstType.text.toString().uppercase()
            tvPokemonSecondType.text.toString().uppercase()
            Picasso.get().load(pokemon.sprites.frontDefault).into(ivPokemon)
            root.setOnClickListener {
                pokemonClickListener.onCardClick(pokemonName = pokemon.name)
            }
        }
    }

    private fun String.uppercase() = this.replaceFirstChar { it.uppercaseChar() }

    inner class PokemonViewHolder(val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface PokemonClickListener {
        fun onCardClick(pokemonName: String)
    }
}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.order == newItem.order
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}