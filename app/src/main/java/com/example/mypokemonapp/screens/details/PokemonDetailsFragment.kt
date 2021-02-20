package com.example.mypokemonapp.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mypokemonapp.adapters.AbilityAdapter
import com.example.mypokemonapp.adapters.MoveAdapter
import com.example.mypokemonapp.databinding.FragmentPokemonDetailsBinding

class PokemonDetailsFragment : Fragment() {

    /**
     * Declaring the view [binding] and [viewModel] variables
     * to be initialized later in the [onCreate] method
     */
    lateinit var binding: FragmentPokemonDetailsBinding
    lateinit var viewModel: PokemonDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.lifecycleOwner = this

        val pokemonResults =
            PokemonDetailsFragmentArgs.fromBundle(requireArguments()).selectedPokemon

        /**
         * Creates ViewModelProvider, which will create ViewModels via the
         * [viewModelFactory]
         * and retain them in a
         * store of the given ViewModelStoreOwner.
         */
        val viewModelFactory = PokemonViewModelFactory(pokemonResults)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(PokemonDetailsViewModel::class.java)


        // binding the layout file to the view model to enable data binding
        binding.detailsViewModel = viewModel

        // Set a new adapter to provide child views on demand.
        binding.movesRecyclerView.adapter = MoveAdapter()
        binding.abilityRecyclerView.adapter = AbilityAdapter()


        return binding.root
    }
}
