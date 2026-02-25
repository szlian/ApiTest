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
                val response = dinoApi.getDino(name)

                if(response.isSuccessful){
                    val dino = response.body()
                    Log.d("DinoViewModel", "Dino name: ${dino?.name}")
                    Log.d("DinoViewModel", "Dino weight: ${dino?.weight}")
                    Log.d("DinoViewModel", "Dino height: ${dino?.height}")
                }else{
                    Log.e("DinoViewModel", "Error: ${response.code()}")
                }
            }catch (e: Exception){
                Log.e("DinoViewModel", "Error: ${e.message}")
            }
        }

    }
}