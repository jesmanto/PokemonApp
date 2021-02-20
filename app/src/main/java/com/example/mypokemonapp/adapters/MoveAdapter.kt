package com.example.mypokemonapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemonapp.data.Move
import com.example.mypokemonapp.data.Moves
import com.example.mypokemonapp.databinding.MovesItemViewBinding

class MoveAdapter : ListAdapter<Moves, MoveAdapter.MoveViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Moves>(){
        override fun areItemsTheSame(oldItem: Moves, newItem: Moves): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Moves, newItem: Moves): Boolean {
            return oldItem.move.name == newItem.move.name
        }

    }

    inner class MoveViewHolder(private val binding : MovesItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (move : Moves) {
            binding.move = move
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        return MoveViewHolder(MovesItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        val move = getItem(position)
        holder.bind(move)
    }
}