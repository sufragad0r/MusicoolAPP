package com.musicoolapp.musicool.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicoolapp.musicool.R
import com.musicoolapp.musicool.componentes.Boton
import com.musicoolapp.musicool.componentes.Formulario
import com.musicoolapp.musicool.componentes.FormularioContrasena
import com.musicoolapp.musicool.componentes.LoCool
import com.musicoolapp.musicool.componentes.Musicool
import com.musicoolapp.musicool.componentes.TextoPresionable
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla


@Composable
fun InicioSesionPantalla(){
    Surface(
        color = colorResource(id = R.color.fondo),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(144.dp))
            Musicool()
            LoCool()
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Formulario(nombre = stringResource(id = R.string.nombreDeUsuario), icono = painterResource(
                    id = R.drawable.usuario
                ))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FormularioContrasena(nombre = "Contraseña", icono = painterResource(id = R.drawable.candado))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                Boton(nombre = "Ingresar", cuandoLoPulsen = {})
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                TextoPresionable(texto = stringResource(id = R.string.noTienesCuenta_pregunta), textoResaltado = stringResource(
                    id = R.string.creaUna
                ), enTextoSeleccionado = {
                    MusicoolEnrutador.navegarHacia(Pantalla.RegistroUsuarioPantalla)
                })
            }
        }
    }
}

@Preview
@Composable
fun VistaPreviaDeInicioDeSesionPantalla(){
    InicioSesionPantalla()
}