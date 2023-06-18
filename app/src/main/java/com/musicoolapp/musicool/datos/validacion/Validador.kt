package com.musicoolapp.musicool.datos.validacion

object Validador {
    fun validarTexto(texto: String): ResultadoDeValidacion {
        return ResultadoDeValidacion(
            (texto.isNullOrBlank())
        )
    }
    fun validarTelefono(telefono: String): ResultadoDeValidacion{
        var error = false
        if (!telefono.matches(Regex("[0-9]+"))){
            error = true
        }
        if (telefono.length != 10){
            error = true
        }
        return ResultadoDeValidacion(
            error
        )
    }
}

data class ResultadoDeValidacion(
    val estado : Boolean = false
)
