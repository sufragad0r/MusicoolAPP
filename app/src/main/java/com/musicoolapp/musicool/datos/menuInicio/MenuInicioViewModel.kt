package com.musicoolapp.musicool.datos.menuInicio

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.red.MusicoolAPI

class MenuInicioViewModel : ViewModel() {

    var menuInicioUIState = mutableStateOf(MenuInicioUIState())

    val isPlaying = mutableStateOf(false)
    val mediaPlayer = MediaPlayer()


    fun togglePlay(filePath: String) {
        if (isPlaying.value) {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(filePath)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
        }
        isPlaying.value = !isPlaying.value
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
            menuInicioUIState.value = menuInicioUIState.value.copy(
                cancionDisponible = false,
            )
             MusicoolAPI().buscarCancion(token, menuInicioUIState.value.nombreCancion, menuInicioUIState.value.artista ){ cancion ->
                if (cancion != null) {
                    menuInicioUIState.value = menuInicioUIState.value.copy(
                        id = cancion.id,
                        fechaDePublicacion = cancion.fechaDePublicacion,
                        artista = cancion.artista,
                        nombreCancion = cancion.nombre,
                        cancionActual = cancion.nombre,
                        artistaActual = cancion.artista
                    )
                    MusicoolAPI().buscarImagen(token, menuInicioUIState.value.id){imagen ->
                        if (imagen != null){
                            menuInicioUIState.value = menuInicioUIState.value.copy(
                                imagen = imagen
                            )
                        }
                    }
                    MusicoolAPI().obtenerCancion(token, menuInicioUIState.value.id){ archivo ->
                        if (archivo != null) {
                            menuInicioUIState.value = menuInicioUIState.value.copy(
                                rutaDelCelularDeCancion = archivo.absolutePath.toString(),
                                cancionDisponible = true

                            )
                            isPlaying.value = false
                        }
                        menuInicioUIState.value = menuInicioUIState.value.copy(
                            nombreCancion = "",
                            artista = "",
                            id = ""
                        )
                    }


                    Log.d("BUSCAR CANCION", "BUSQUEDA VALIDA")
                }

                }
            }
    }

    fun playMusic(filePath: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(filePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun stopMusic() {
        mediaPlayer.stop()
    }

    override fun onCleared() {
        mediaPlayer.release()
        super.onCleared()
    }


}