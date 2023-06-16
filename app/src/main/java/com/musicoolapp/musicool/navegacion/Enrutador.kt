package com.musicoolapp.musicool.navegacion

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Pantalla {

    object InicioSesionPantalla : Pantalla()
    object CodigoOTPPantalla : Pantalla()
    object RegistroUsuarioPantalla : Pantalla()
    object MenuInicioPantalla : Pantalla()
}


object MusicoolEnrutador {

    var pantallaActual: MutableState<Pantalla> = mutableStateOf(Pantalla.InicioSesionPantalla)

    fun navegarHacia(destino : Pantalla){
        pantallaActual.value = destino
    }


}