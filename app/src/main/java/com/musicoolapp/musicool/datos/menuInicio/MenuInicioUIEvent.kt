package com.musicoolapp.musicool.datos.menuInicio

sealed class MenuInicioUIEvent {
    data class nombreCancionCambio(val nombreCancion:String) : MenuInicioUIEvent()
    data class artistaCambio(val artista: String) : MenuInicioUIEvent()

}