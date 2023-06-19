package com.musicoolapp.musicool.datos.menuInicio

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MenuInicioViewModel : ViewModel() {

    var menuInicioUIState = mutableStateOf(MenuInicioUIState())

    fun onEvent(event: MenuInicioUIEvent){
        when(event){
            is MenuInicioUIEvent.nombreCancionCambio -> {
                menuInicioUIState.value = menuInicioUIState.value.copy(
                    nombreCancion = event.nombreCancion
                )
            }
            is MenuInicioUIEvent.artistaCambio -> {
                menuInicioUIState.value = menuInicioUIState.value.copy(
                    artista = event.artista
                )
            }
            is MenuInicioUIEvent.botonBuscarClickeado ->{
                buscarCancion()
            }
        }
    }

    private fun buscarCancion() {

        if (menuInicioUIState.value.nombreCancion.isNullOrBlank() && menuInicioUIState.value.artista.isNullOrBlank()){
            menuInicioUIState.value = menuInicioUIState.value.copy(
                nombreCancionError = menuInicioUIState.value.nombreCancion.isNullOrBlank(),
                artistaError = menuInicioUIState.value.artista.isNullOrBlank()
            )
        }else{
            Log.d("BUSCAR CANCION", "BUSQUEDA VALIDA")
        }

    }


}