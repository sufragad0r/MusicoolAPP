package com.musicoolapp.musicool.datos.codigoOTP

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.datos.validacion.Validador
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla

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
            is CodigoOTPUIEvent.botonDeEnviarClickeado -> {
                irAlMenuPrincipal()
            }
        }
    }

    private fun irAlMenuPrincipal() {
        if(codigoOTPUIState.value.codigoOTPError){
            Log.d("CODIGO OTP", "Codigo OTP invalido")
            mostrarEstado()
        }else{
            Log.d("CODIGO OTP", "Codigo OTP valido")
            MusicoolEnrutador.navegarHacia(Pantalla.MenuInicioPantalla)
        }
    }

    private fun mostrarEstado(){
        Log.d("CODIGO OTP", codigoOTPUIState.value.toString())
    }
}