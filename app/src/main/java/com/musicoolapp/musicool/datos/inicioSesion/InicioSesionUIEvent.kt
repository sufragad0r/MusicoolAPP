package com.musicoolapp.musicool.datos.inicioSesion

sealed class InicioSesionUIEvent {
    data class nombreUsuarioCambio(val nombreUsuario: String) : InicioSesionUIEvent()
    data class contrasenaCambio(val contrasena: String) : InicioSesionUIEvent()

    object botonDeIniciarSesionClickeado : InicioSesionUIEvent()
}