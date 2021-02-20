package com.example.mypokemonapp.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mypokemonapp.adapters.PokemonAdapter
import com.example.mypokemonapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        /**
         * Binding the viewModel and the lifeCyleOwner tt
         * the layout files
         */
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.onStart()

        /**
         * This button is clicked to show the editTextBox to
         * input the search details
         */
        binding.searchBtn.setOnClickListener {
            it.visibility = View.GONE
            handleEditTextBox()
        }

        /**
         * Setting a click listener for the request button
         */
        binding.makeRequest.setOnClickListener {
            try {
                fetchPokemon()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(), "Cannot search without entries",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        /**
         * Assigning an observer to the onRequest livedata variable
         * to know which function to call
         */
        viewModel.onRequestPokemon.observe(viewLifecycleOwner, Observer { requestPokemon ->
            if (requestPokemon) {
                val limit = Integer.valueOf(binding.pokemonLimitQty.text.toString())
                val offset = Integer.valueOf(binding.pokemonOffsetQty.text.toString())
                viewModel.getPokemonCharacters(limit, offset)
            } else {
                viewModel.getPokemonCharacters(20, 0)
            }
        })

        // Set a new adapter to provide child views on demand.
        binding.pokemonGridView.adapter = PokemonAdapter(PokemonAdapter.OnClickListener {
            viewModel.displayPokemonDetails(it)
        })

        binding.uploadFragmentLink.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToImageUploadFragment()
            this.findNavController().navigate(action)
        }

        /**
         * Observing the navigation variable from the viewModel to
         * know when to navigate to the next fragment
         */
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPokemonDetailsFragment(it)
                )
                viewModel.displayPokemonDetailsComplete()
            }
        })
        return binding.root
    }

    /**
     * This function is called when a user clicks on the search button to perform
     * a custom search. It takes limit and offset parameters to be passed into the
     * API function
     */
    private fun fetchPokemon() {
        binding.searchBox.visibility = View.GONE
        binding.searchBtn.visibility = View.VISIBLE
        closeEditTextBox()
        viewModel.onRequest()
    }

    /**
     * This method handles the activities of the edit tex box that
     * receive users input for a custom query, when the first
     * button is clicked
     */
    private fun handleEditTextBox() {
        binding.pokemonLimitQty.text.clear()
        binding.pokemonOffsetQty.text.clear()
        binding.searchBox.visibility = View.VISIBLE
    }

    /**
     * This method handles the activities of the edit tex box that
     * receive users input for a custom query, when the request
     * button is clicked
     */
    private fun closeEditTextBox() {
        binding.pokemonLimitQty.clearFocus()
        binding.pokemonOffsetQty.clearFocus()
    }
}