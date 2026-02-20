package com.example.apitest.network

import com.example.apitest.model.Pokemon
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Esta es una interfaz de Retrofit. Las interfaces son como un contrato: definen QUÉ
 * peticiones se pueden hacer, pero no CÓMO se hacen. Retrofit se encargará de la
 * implementación interna.
 *
 * El nombre 'PokeAPIClient' es descriptivo, indicando que este cliente es para la PokeAPI.
 */
interface PokeAPIClient {

// En alguna otra clase, como un ViewModel o un Repositorio...

    // Supongamos que ya tienes 'apiClient' creado e inicializado
    /*val apiClient: PokeAPIClient = // ... inicialización de Retrofit
    fun fetchPokemonData() {
        // Se lanza una corrutina para no bloquear el hilo principal
        viewModelScope.launch {
            try {
                // Se llama a la función de la interfaz
                val response = apiClient.getPokemon()
//test


                // Se comprueba si la petición fue exitosa (código 2xx)
                if (response.isSuccessful) {
                    // Se obtiene el cuerpo de la respuesta, que es el objeto Pokemon
                    val pokemon = response.body()
                    if (pokemon != null) {
                        // ¡Éxito! Aquí puedes usar los datos del pokemon
                        println("Pokémon encontrado: ${pokemon.name}")
                        println("ID: ${pokemon.id}")
                    }
                } else {
                    // La petición falló (ej: error 404, 500)
                    println("Error al obtener el Pokémon: ${response.code()}")
                }
            } catch (e: Exception) {
                // Ocurrió una excepción, por ejemplo, no hay conexión a internet
                println("Error de red: ${e.message}")
            }
        }
    }*/

    @GET ("species-list?")
    suspend fun getPokemon(

        @Query("genus") genus: String = "Abies"

    )

}