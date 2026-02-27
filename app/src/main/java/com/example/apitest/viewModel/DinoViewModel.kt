package com.example.apitest.viewModel

import android.R.attr.name
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.network.DinoAPIClient
import com.example.apitest.network.RetrofitBuilder
import kotlinx.coroutines.launch

class DinoViewModel: ViewModel() {
    private val retrofit = RetrofitBuilder.build()
    private val dinoApi = retrofit.create(DinoAPIClient::class.java)

    fun getDino(){
        viewModelScope.launch {
            try{
                val response = dinoApi.getDino(
                    name = "adamtisaurus",
                    weight = 14000,
                    height = 4,
                    length = 18,
                    diet = "herbivore",
                    period = "late cretaceous"
                )

            }catch (e: Exception){
                Log.e("DinoViewModel", "Error: ${e.message}")
            }
        }

    }
}