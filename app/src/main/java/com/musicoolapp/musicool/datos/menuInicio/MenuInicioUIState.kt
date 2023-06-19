package com.musicoolapp.musicool.datos.menuInicio

data class MenuInicioUIState(
    var nombreCancion: String = "",
    var artista: String = "",

    var nombreCancionError: Boolean = false,
    var artistaError: Boolean = false
)