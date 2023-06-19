package com.musicoolapp.musicool.datos.menuInicio

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.red.MusicoolAPI
import com.musicoolapp.musicool.sesion.Sesion

class MenuInicioViewModel : ViewModel() {

    var menuInicioUIState = mutableStateOf(MenuInicioUIState())

    val mediaPlayer: MediaPlayer = MediaPlayer()
    val isPlaying = mutableStateOf(false)

    fun togglePlay() {
        if (isPlaying.value) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        isPlaying.value = !isPlaying.value
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }

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
        }
    }

    fun buscarCancion(token: String) {

        if (menuInicioUIState.value.nombreCancion.isNullOrBlank() && menuInicioUIState.value.artista.isNullOrBlank()){
            menuInicioUIState.value = menuInicioUIState.value.copy(
                nombreCancionError = menuInicioUIState.value.nombreCancion.isNullOrBlank(),
                artistaError = menuInicioUIState.value.artista.isNullOrBlank()
            )
        }else{
            MusicoolAPI().buscarCancion(token, menuInicioUIState.value.nombreCancion, menuInicioUIState.value.artista ){ id ->
                menuInicioUIState.value = menuInicioUIState.value.copy(
                    id = id.toString()
                )
                Log.d("BUSCAR CANCION", "BUSQUEDA VALIDA")
            }
        }

    }


}