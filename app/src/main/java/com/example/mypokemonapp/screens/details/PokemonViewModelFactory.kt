package com.example.mypokemonapp.screens.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypokemonapp.data.PokemonResults
import java.lang.IllegalArgumentException

class PokemonViewModelFactory (
    private val pokemonResults: PokemonResults) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)){
            return PokemonDetailsViewModel(pokemonResults) as T
        } else {
            throw IllegalArgumentException("unknown ViewModel class")
        }
    }
}