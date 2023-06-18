package com.musicoolapp.musicool.datos.inicioSesion

data class InicioSesionUIState(
    var nombreUsuario: String = "",
    var contrasena: String = "",

    var nombreUsuarioError: Boolean = false,
    var contrasenaError: Boolean = false
)