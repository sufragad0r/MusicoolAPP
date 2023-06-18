package com.musicoolapp.musicool.datos.registroUsuario

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.datos.validacion.Validador

class RegistroUsuarioViewModel : ViewModel() {
    var registroUsuarioUIState = mutableStateOf(RegistroUsuarioUIState())

    fun onEvent(event : RegistroUsuarioUIEvent){
        when(event){
            is RegistroUsuarioUIEvent.nombreUsuarioCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    nombreUsuario = event.nombreUsuario
                )
            }
            is RegistroUsuarioUIEvent.telefonoCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    telefono = event.telefono
                )
            }
            is RegistroUsuarioUIEvent.contrasenaCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    contrasena = event.contrasena
                )
            }
            is RegistroUsuarioUIEvent.botonDeCrearCuentaClickeado -> {
                registrarUsuario()
            }
        }
        validarDatos()
    }

    private fun registrarUsuario() {
        if(registroUsuarioUIState.value.nombreUsuarioError || registroUsuarioUIState.value.telefonoError || registroUsuarioUIState.value.contrasenaError){
            Log.d("REGISTRO FALLIDO", "Datos invalidos")
            mostrarEstado()
        }else{
            Log.d("REGISTRO EXITOSO", "Datos validos")
            mostrarEstado()
        }
    }

    private fun validarDatos() {
        registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
            nombreUsuarioError = Validador.validarTexto(registroUsuarioUIState.value.nombreUsuario).estado,
            telefonoError = Validador.validarTelefono(registroUsuarioUIState.value.telefono).estado,
            contrasenaError = Validador.validarTexto(registroUsuarioUIState.value.contrasena).estado
        )
    }

    private fun mostrarEstado(){
        Log.d("REGISTRO", registroUsuarioUIState.value.toString())
    }
}

