package com.musicoolapp.musicool.datos.inicioSesion

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.datos.validacion.Validador
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla
import com.musicoolapp.musicool.red.MusicoolAPI

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
                iniciarSesion(inicioSesionUIState.value.nombreUsuario, inicioSesionUIState.value.contrasena)
            }
        }
    }

    private fun iniciarSesion(nombreUsuario: String, contrasena: String) {
        if (inicioSesionUIState.value.nombreUsuarioError || inicioSesionUIState.value.contrasenaError) {
            Log.d("REGISTRO FALLIDO", "Datos inválidos")
        } else {
            MusicoolAPI().iniciarSesion(nombreUsuario, contrasena) { otpRespuesta ->
                if (otpRespuesta != null) {
                    inicioSesionUIState.value = inicioSesionUIState.value.copy(
                        otpRespuesta = otpRespuesta
                    )
                    if (otpRespuesta.solicitarOTP == "True") {

                        Log.d("REGISTRO EXITOSO", "Datos válidos")
                        MusicoolEnrutador.navegarHacia(Pantalla.CodigoOTPPantalla)
                    }
                } else {
                    Log.d("REGISTRO FALLIDO", "Error del servidor")
                }
            }
        }
    }

    private fun mostrarEstado(){
        Log.d("INICIO SESION", inicioSesionUIState.value.toString())
    }
}

