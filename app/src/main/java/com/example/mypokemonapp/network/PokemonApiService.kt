package com.example.mypokemonapp.network

import com.example.mypokemonapp.data.Pokemon
import com.example.mypokemonapp.data.Root
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

private const val BASE_URL = "https://pokeapi.co/api/v2/"

// Creating Moshi instance
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Creating a retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * This interface
 */
interface PokemonApiService {
    @GET("pokemon")
    fun getPropertiesAsync(@Query("limit") end:Int, @Query("offset") start:Int):
            Call<Root>

    @GET("pokemon/{id}/")
    fun getPokemon(@Path("id") id: String): Call<Pokemon>
}

object PokemonApi {

    val retrofitService : PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}