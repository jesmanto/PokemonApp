package com.example.mypokemonapp.screens.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokemonapp.data.*
import com.example.mypokemonapp.network.PokemonApi
import com.example.mypokemonapp.network.PokemonApi.retrofitService
import kotlinx.coroutines.launch
import retrofit2.await

class PokemonDetailsViewModel (pokemonResults: PokemonResults): ViewModel() {

    /**
     *
     */
    private val _selectedPokemon = MutableLiveData<PokemonResults>()
    val selectedResult : LiveData<PokemonResults>
        get() = _selectedPokemon

    private var _move = MutableLiveData<List<Move>>()
    val move : LiveData<List<Move>>
        get() = _move

    private var _abilities = MutableLiveData<List<Ability>>()
    val abilities : LiveData<List<Ability>>
        get() = _abilities

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon : LiveData<Pokemon>
        get() = _pokemon

    init {
        _selectedPokemon.value = pokemonResults
        getPokemonDetails(getId(pokemonResults.url))
    }


    private fun getPokemonDetails(id: String) {
        Log.d("Pokemon", "Method started: $id")
        viewModelScope.launch {
            try {
                // create and start the network call on the background thread
                val pokemon = retrofitService.getPokemon(id).await()
                Log.d("Pokemon moves: ", "$pokemon")

                _pokemon.value = pokemon

            } catch (e: Exception) {

            }
        }
    }
}