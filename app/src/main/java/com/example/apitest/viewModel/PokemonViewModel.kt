package com.example.apitest.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.network.PokeAPIClient
import com.example.apitest.network.retrofitBuilder
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {
    private val retrofit = retrofitBuilder().build()
    private val pokeApi = retrofit.create(PokeAPIClient::class.java)

    fun getEmolga(){
        viewModelScope.launch {
            try{
                val response = pokeApi.getPokemon()

                if(response.isSuccessful){
                    val emolga = response.body()
                    Log.d("PokemonViewModel", "Pokemon name: ${emolga?.name}")
                    Log.d("PokemonViewModel", "Pokemon weight: ${emolga?.id}")
                    Log.d("PokemonViewModel", "Pokemon height: ${emolga?.height}")
                }else{
                    Log.e("PokemonViewModel", "Error: ${response.code()}")
                }
            }catch (e: Exception){
                Log.e("PokemonViewModel", "Error: ${e.message}")
            }
        }

    }
}