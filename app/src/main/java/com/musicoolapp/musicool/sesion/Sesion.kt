package com.musicoolapp.musicool.sesion

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Sesion(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Sesion")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
        val TOKEN = stringPreferencesKey("token")
    }

    val conseguirNombreUsuario: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[NOMBRE_USUARIO] ?: ""
        }

    suspend fun guardarNombreUsuario(nombre: String) {
        context.dataStore.edit { preferences ->
            preferences[NOMBRE_USUARIO] = nombre
        }
    }

    val conseguirToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN] ?: ""
        }

    suspend fun guardarToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }
}