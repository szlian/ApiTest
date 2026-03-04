package com.example.apitest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.FavoritesDataStore
import com.example.apitest.model.Dinosaur
import com.example.apitest.network.RetrofitInstance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DinoViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesDataStore = FavoritesDataStore(application)

    // Lista de todos los dinosaurios
    private val _dinos = MutableStateFlow<List<Dinosaur>>(emptyList())
    val dinos: StateFlow<List<Dinosaur>> = _dinos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // IDs de favoritos
    val favoriteIds: StateFlow<Set<String>> = favoritesDataStore.favoriteIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

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
                println("Dinosaurios cargados: ${list.size}")
                // Imprimir el primero para verificar que tiene descripción
                list.firstOrNull()?.let {
                    println("Primer dino: ${it.name}, descripción: ${it.description?.take(50)}...")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(dinoId: String) {
        viewModelScope.launch {
            favoritesDataStore.toggleFavorite(dinoId)
        }
    }

    // IMPORTANTE: Buscar dinosaurio por ID de forma segura
    fun getDinoById(id: String): Dinosaur? {
        return _dinos.value.find { it.id == id }.also {
            println("Buscando dino ID: $id, encontrado: ${it != null}")
            if (it != null) {
                println("Descripción: ${it.description?.take(100)}...")
            }
        }
    }

    val favoriteDinos: StateFlow<List<Dinosaur>> = combine(dinos, favoriteIds) { allDinos, favIds ->
        allDinos.filter { it.id in favIds }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}