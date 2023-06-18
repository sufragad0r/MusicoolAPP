package com.musicoolapp.musicool.datos.registroUsuario

data class RegistroUsuarioUIState(
    var nombreUsuario: String = "",
    var telefono: String = "",
    var contrasena : String = "",

    var nombreUsuarioError: Boolean = false,
    var telefonoError: Boolean = false,
    var contrasenaError: Boolean = false
)