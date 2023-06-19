package com.musicoolapp.musicool.datos.menuInicio

import androidx.compose.ui.graphics.ImageBitmap

data class MenuInicioUIState(
    var nombreCancion: String = "",
    var artista: String = "",
    var fechaDePublicacion: String = "",
    var id: String = "",
    var rutaDelCelularDeCancion: String = "",
    var imagen: ImageBitmap? = null,
    var cancionDisponible: Boolean = false,

    var nombreCancionError: Boolean = false,
    var artistaError: Boolean = false
)