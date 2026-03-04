package com.example.apitest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.model.Dinosaur
import com.example.apitest.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DinoViewModel : ViewModel() {

    private val _dinos = MutableStateFlow<List<Dinosaur>>(emptyList())
    val dinos: StateFlow<List<Dinosaur>> = _dinos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchDinosaurs()
    }

    private fun fetchDinosaurs() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val list = RetrofitInstance.api.getDinosaurs()
                _dinos.value = list
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
}