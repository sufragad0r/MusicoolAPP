package com.musicoolapp.musicool.datos.codigoOTP

sealed class CodigoOTPUIEvent {
    data class codigoOTPCambio(val codigoOTP: String): CodigoOTPUIEvent()

}