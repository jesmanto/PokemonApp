package com.example.mypokemonapp.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mypokemonapp.R
import com.example.mypokemonapp.data.Ability
import com.example.mypokemonapp.data.Moves
import com.example.mypokemonapp.data.PokemonResults

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<PokemonResults>?
) {
    val adapter = recyclerView.adapter as PokemonAdapter
    adapter.submitList(data)
}

@BindingAdapter("movesList")
fun bindMoves(
    recyclerView: RecyclerView,
    data: List<Moves>?
) {
    val adapter = recyclerView.adapter as MoveAdapter
    adapter.submitList(data)
}

@BindingAdapter("abilityList")
fun bindAbility(
    recyclerView: RecyclerView,
    data: List<Ability>?
) {
    val adapter = recyclerView.adapter as AbilityAdapter
    adapter.submitList(data)
}