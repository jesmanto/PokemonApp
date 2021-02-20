package com.example.mypokemonapp.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokemonapp.data.Pokemon
import com.example.mypokemonapp.data.PokemonResults
import com.example.mypokemonapp.data.Root
import com.example.mypokemonapp.network.PokemonApi
import com.example.mypokemonapp.network.PokemonApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

class HomeViewModel : ViewModel() {
    var pokemonLinkUrl = ""
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Creates a livedata variable for list of pokemon results
     * that can be observed
     */
    private val _root = MutableLiveData<List<PokemonResults>>()
    val root: LiveData<List<PokemonResults>>
        get() = _root


    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    /**
     * Creates a livedata variable to check for navigation
     * clicks so it can move the Pokemon results along with it
     * that can be observed
     */
    private val _navigateToSelectedProperty = MutableLiveData<PokemonResults>()
    val navigateToSelectedProperty: LiveData<PokemonResults>
        get() = _navigateToSelectedProperty

    /**
     * Creates a livedata variable
     * that can be observed to know
     * when to call a request method
     */
    private var _onRequestPokemon = MutableLiveData<Boolean>()
    val onRequestPokemon: LiveData<Boolean>
        get() = _onRequestPokemon


    /**
     * This methods fires the API Retrofit service
     * to pull data from the API server
     */
    fun getPokemonCharacters(end: Int, start: Int) {
        viewModelScope.launch {

            try {
                Log.d("Checking", "Ok: try started")
                val listResult = PokemonApi.retrofitService.getPropertiesAsync(end, start).await()
                if (listResult.results.isNotEmpty()) {
                    Log.d("Checking", listResult.results.toString())
                    _root.value = listResult.results
                }
            } catch (e: Exception) {
                Log.d("Checking: ", "Unable to pull GET data")
            }
        }
    }


    fun displayPokemonDetails(pokemonResults: PokemonResults) {
        _navigateToSelectedProperty.value = pokemonResults
    }

    fun displayPokemonDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Starts the coroutine CALL request once the fragment
     * is attached
     */
    fun onStart() {
        _onRequestPokemon.value = false
    }

    /**
     * Makes a query to the server with the query path collected from the search box
     */
    fun onRequest() {
        _onRequestPokemon.value = true
    }
}
