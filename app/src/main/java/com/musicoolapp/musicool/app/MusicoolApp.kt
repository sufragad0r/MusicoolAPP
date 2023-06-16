package com.musicoolapp.musicool.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla
import com.musicoolapp.musicool.pantallas.CodigoOTPPantalla
import com.musicoolapp.musicool.pantallas.InicioSesionPantalla
import com.musicoolapp.musicool.pantallas.MenuInicioPantalla
import com.musicoolapp.musicool.pantallas.RegistroUsuarioPantalla


@Composable
fun MusicoolApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White){
        Crossfade(targetState = MusicoolEnrutador.pantallaActual) { pantalla ->
            when (pantalla.value) {
                is Pantalla.InicioSesionPantalla -> {
                    InicioSesionPantalla()
                }

                is Pantalla.CodigoOTPPantalla -> {
                    CodigoOTPPantalla()
                }

                is Pantalla.RegistroUsuarioPantalla -> {
                    RegistroUsuarioPantalla()
                }

                is Pantalla.MenuInicioPantalla -> {
                    MenuInicioPantalla()
                }
            }
        }
    }
}