package com.example.apitest.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

class FavoritesDataStore(private val context: Context) {
    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_dinos")

    val favoriteIds: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun toggleFavorite(dinoId: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = if (currentFavorites.contains(dinoId)) {
                currentFavorites - dinoId
            } else {
                currentFavorites + dinoId
            }
        }
    }

    suspend fun isFavorite(dinoId: String): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[FAVORITES_KEY]?.contains(dinoId) ?: false
        }.first()
    }
}