package com.musicoolapp.musicool.datos.inicioSesion

import com.musicoolapp.musicool.red.SolicitarOTP

data class InicioSesionUIState(
    var nombreUsuario: String = "",
    var contrasena: String = "",
    var otpRespuesta: SolicitarOTP = SolicitarOTP("",""),

    var nombreUsuarioError: Boolean = false,
    var contrasenaError: Boolean = false
)