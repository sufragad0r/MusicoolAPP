package com.musicoolapp.musicool.datos.menuInicio

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.red.MusicoolAPI

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
        }
    }

     fun buscarCancion(token: String) {

        if (menuInicioUIState.value.nombreCancion.isNullOrBlank() && menuInicioUIState.value.artista.isNullOrBlank()){
            menuInicioUIState.value = menuInicioUIState.value.copy(
                nombreCancionError = menuInicioUIState.value.nombreCancion.isNullOrBlank(),
                artistaError = menuInicioUIState.value.artista.isNullOrBlank()
            )
        }else{
             MusicoolAPI().buscarCancion(token, menuInicioUIState.value.nombreCancion, menuInicioUIState.value.artista ){ cancion ->
                if (cancion != null) {
                    menuInicioUIState.value = menuInicioUIState.value.copy(
                        id = cancion.id,
                        fechaDePublicacion = cancion.fechaDePublicacion,
                        artista = cancion.artista,
                        nombreCancion = cancion.nombre
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
                                rutaDelCelularDeCancion = archivo.absolutePath.toString())
                        }
                    }

                    Log.d("BUSCAR CANCION", "BUSQUEDA VALIDA")
                }

                }
            }



    }


}