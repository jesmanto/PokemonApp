package com.example.mypokemonapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.data.Ability
import com.example.mypokemonapp.databinding.AbilityItemViewBinding

class AbilityAdapter : ListAdapter<Ability, AbilityAdapter.AbilityViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Ability>(){
        override fun areItemsTheSame(oldItem: Ability, newItem: Ability): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ability, newItem: Ability): Boolean {
            return oldItem.ability.name == newItem.ability.name
        }

    }

    inner class AbilityViewHolder(private val binding : AbilityItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (ability : Ability) {
            binding.ability = ability
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        return AbilityViewHolder(AbilityItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val ability = getItem(position)
        holder.bind(ability)
    }
}