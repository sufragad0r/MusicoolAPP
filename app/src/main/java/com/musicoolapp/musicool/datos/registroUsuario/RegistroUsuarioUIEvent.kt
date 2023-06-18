package com.musicoolapp.musicool.datos.registroUsuario

sealed class RegistroUsuarioUIEvent {
    data class nombreUsuarioCambio(val nombreUsuario: String) : RegistroUsuarioUIEvent()
    data class telefonoCambio(val telefono: String) : RegistroUsuarioUIEvent()
    data class contrasenaCambio(val contrasena: String) : RegistroUsuarioUIEvent()

    object botonDeCrearCuentaClickeado : RegistroUsuarioUIEvent()
}