package com.example.mypokemonapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.data.Pokemon
import com.example.mypokemonapp.data.PokemonResults
import com.example.mypokemonapp.data.Root
import com.example.mypokemonapp.databinding.PokemonListViewBinding


class PokemonAdapter (private val onClickListener: OnClickListener) : ListAdapter<PokemonResults,PokemonAdapter.PokemonViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonResults>() {
        override fun areItemsTheSame(oldItem: PokemonResults, newItem: PokemonResults): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PokemonResults, newItem: PokemonResults): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }
    }

    inner class PokemonViewHolder(private val binding : PokemonListViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemonResults: PokemonResults) {
            binding.details = pokemonResults
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonResults = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemonResults)
        }
        holder.bind(pokemonResults)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(PokemonListViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class OnClickListener(val clickListener: (pokemonResults : PokemonResults) -> Unit) {
        fun onClick(pokemonResults: PokemonResults) = clickListener(pokemonResults)
    }
}