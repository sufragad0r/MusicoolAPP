package com.musicoolapp.musicool.datos.inicioSesion

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.datos.validacion.Validador
import kotlin.math.log

class InicioSesionViewModel : ViewModel() {

    var inicioSesionUIState = mutableStateOf(InicioSesionUIState())

    fun onEvent(event: InicioSesionUIEvent){
        when(event){
            is InicioSesionUIEvent.nombreUsuarioCambio -> {
                inicioSesionUIState.value = inicioSesionUIState.value.copy(
                    nombreUsuario = event.nombreUsuario
                )
                inicioSesionUIState.value = inicioSesionUIState.value.copy(
                    nombreUsuarioError = Validador.validarTexto(inicioSesionUIState.value.nombreUsuario).estado
                )
            }
            is InicioSesionUIEvent.contrasenaCambio -> {
                inicioSesionUIState.value = inicioSesionUIState.value.copy(
                    contrasena = event.contrasena
                )
                inicioSesionUIState.value = inicioSesionUIState.value.copy(
                    contrasenaError = Validador.validarTexto(inicioSesionUIState.value.contrasena).estado
                )
            }
            is InicioSesionUIEvent.botonDeIniciarSesionClickeado -> {
                iniciarSesion()
            }
        }
    }

    private fun iniciarSesion() {
        if(inicioSesionUIState.value.nombreUsuarioError || inicioSesionUIState.value.contrasenaError){
            Log.d("REGISTRO FALLIDO", "Datos invalidos")
            mostrarEstado()
        }else{
            Log.d("REGISTRO EXITOSO", "Datos validos")
            mostrarEstado()
        }
    }

    private fun mostrarEstado(){
        Log.d("INICIO SESION", inicioSesionUIState.value.toString())
    }
}