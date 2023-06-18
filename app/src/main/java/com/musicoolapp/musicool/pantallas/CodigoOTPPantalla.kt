package com.musicoolapp.musicool.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musicoolapp.musicool.R
import com.musicoolapp.musicool.componentes.Boton
import com.musicoolapp.musicool.componentes.FormularioContrasena
import com.musicoolapp.musicool.componentes.Musicool
import com.musicoolapp.musicool.componentes.TextoBold
import com.musicoolapp.musicool.componentes.TextoMedio
import com.musicoolapp.musicool.componentes.TextoPresionable
import com.musicoolapp.musicool.componentes.TextoSemiBold
import com.musicoolapp.musicool.datos.codigoOTP.CodigoOTPUIEvent
import com.musicoolapp.musicool.datos.codigoOTP.CodigoOTPViewModel
import com.musicoolapp.musicool.datos.inicioSesion.InicioSesionUIEvent
import com.musicoolapp.musicool.navegacion.MusicoolEnrutador
import com.musicoolapp.musicool.navegacion.Pantalla

@Composable
fun CodigoOTPPantalla(codigoOTPViewModel: CodigoOTPViewModel = viewModel()) {
    Surface(
        color = colorResource(id = R.color.fondo),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(160.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                TextoSemiBold(texto = "Ayudanos a", color = colorResource(id = R.color.texto), tamano = 50, modifier = Modifier)
            }
            Row(
                modifier = Modifier.fillMaxWidth().heightIn(50.dp),
                horizontalArrangement = Arrangement.Center
            ){
                TextoBold(texto = "protegerte", color = colorResource(id = R.color.moradoCool), tamano = 50, modifier = Modifier)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ){
                TextoMedio(texto = "Por eso mandamos un codigo a tu telefono", color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier)
            }
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FormularioContrasena(nombre = "Introduce el codigo", icono = painterResource(id = R.drawable.candado),
                    onTextSelected = {
                        codigoOTPViewModel.onEvent(CodigoOTPUIEvent.codigoOTPCambio(it))
                    }, hayError = codigoOTPViewModel.codigoOTPUIState.value.codigoOTPError
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                Boton(nombre = "Enviar", cuandoLoPulsen = {
                    codigoOTPViewModel.onEvent(
                        CodigoOTPUIEvent.botonDeEnviarClickeado)
                })
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview
@Composable
fun VistaPreviaDeCodigoOTP(){
    CodigoOTPPantalla()
}