package com.musicoolapp.musicool.datos.codigoOTP

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.datos.validacion.Validador
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla
import com.musicoolapp.musicool.red.MusicoolAPI
import com.musicoolapp.musicool.sesion.Sesion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CodigoOTPViewModel : ViewModel(){
    var codigoOTPUIState = mutableStateOf(
        CodigoOTPUIState()
    )

    fun onEvent(event: CodigoOTPUIEvent){
        when(event){
            is CodigoOTPUIEvent.codigoOTPCambio -> {
                codigoOTPUIState.value = codigoOTPUIState.value.copy(
                    codigoOTP = event.codigoOTP
                )
                codigoOTPUIState.value = codigoOTPUIState.value.copy(
                    codigoOTPError = Validador.validarCodigoOTP(codigoOTPUIState.value.codigoOTP).estado
                )
            }
        }
    }

    fun irAlMenuPrincipal(nombreUsuario: String, dataStore: Sesion, scope: CoroutineScope) {
        if(codigoOTPUIState.value.codigoOTPError){
            Log.d("CODIGO OTP", "Codigo OTP invalido")
            mostrarEstado()
        }else{
            MusicoolAPI().codigoOTP(nombreUsuario, codigoOTPUIState.value.codigoOTP){ token ->
                if (token != null) {
                    if (token.access_token != null) {
                        scope.launch {
                            dataStore.guardarToken(token.access_token)
                        }
                        MusicoolEnrutador.navegarHacia(Pantalla.MenuInicioPantalla)
                        Log.d("CODIGO OTP EXITOSO", dataStore.toString())
                    } else {
                        Log.d("REGISTRO FALLIDO", "Error del servidor")
                        MusicoolEnrutador.navegarHacia(Pantalla.InicioSesionPantalla)
                    }
                }
            }
        }
    }

    private fun mostrarEstado(){
        Log.d("CODIGO OTP", codigoOTPUIState.value.toString())
    }
}