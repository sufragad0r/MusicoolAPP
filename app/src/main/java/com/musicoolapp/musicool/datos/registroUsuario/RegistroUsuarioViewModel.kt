package com.musicoolapp.musicool.datos.registroUsuario

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.musicoolapp.musicool.app.MusicoolApp
import com.musicoolapp.musicool.datos.validacion.Validador
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla
import com.musicoolapp.musicool.red.MusicoolAPI

class RegistroUsuarioViewModel : ViewModel() {
    var registroUsuarioUIState = mutableStateOf(RegistroUsuarioUIState())

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event : RegistroUsuarioUIEvent){
        when(event){
            is RegistroUsuarioUIEvent.nombreUsuarioCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    nombreUsuario = event.nombreUsuario
                )
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    nombreUsuarioError = Validador.validarTexto(registroUsuarioUIState.value.nombreUsuario).estado
                )
            }
            is RegistroUsuarioUIEvent.telefonoCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    telefono = event.telefono
                )
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    telefonoError = Validador.validarTelefono(registroUsuarioUIState.value.telefono).estado
                )
            }
            is RegistroUsuarioUIEvent.contrasenaCambio -> {
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    contrasena = event.contrasena
                )
                registroUsuarioUIState.value = registroUsuarioUIState.value.copy(
                    contrasenaError = Validador.validarTexto(registroUsuarioUIState.value.contrasena).estado
                )
            }
            is RegistroUsuarioUIEvent.botonDeCrearCuentaClickeado -> {
                registrarUsuario()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarUsuario() {
        if(registroUsuarioUIState.value.nombreUsuarioError || registroUsuarioUIState.value.telefonoError || registroUsuarioUIState.value.contrasenaError){
            Log.d("REGISTRO FALLIDO", "Datos invalidos")
            mostrarEstado()
        }else{
            val usuario = MusicoolAPI.Usuario(
                username = registroUsuarioUIState.value.nombreUsuario,
                password = registroUsuarioUIState.value.contrasena,
                telefono = "+52"+registroUsuarioUIState.value.telefono
            )

            MusicoolAPI().registrarUsuario(usuario) { exito ->
                if (!exito) {
                    Log.d("REGISTRO EXITOSO", "Datos validos")
                    MusicoolEnrutador.navegarHacia(Pantalla.InicioSesionPantalla)
                } else {
                    Log.d("Registro fallido", "Error al registrar el usuario")
                }
            }

        }
    }

    private fun mostrarEstado(){
        Log.d("REGISTRO", registroUsuarioUIState.value.toString())
    }
}

