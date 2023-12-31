package com.musicoolapp.musicool.pantallas

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musicoolapp.musicool.R
import com.musicoolapp.musicool.componentes.Boton
import com.musicoolapp.musicool.componentes.Formulario
import com.musicoolapp.musicool.componentes.LoCool
import com.musicoolapp.musicool.componentes.Musicool
import com.musicoolapp.musicool.componentes.TextoBold
import com.musicoolapp.musicool.componentes.TextoSemiBold
import com.musicoolapp.musicool.datos.menuInicio.MenuInicioUIEvent
import com.musicoolapp.musicool.datos.menuInicio.MenuInicioViewModel
import com.musicoolapp.musicool.sesion.Sesion

@Composable
fun MenuInicioPantalla(menuInicioViewModel: MenuInicioViewModel = viewModel()) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val dataStore = Sesion(context)

    val token: String = dataStore.conseguirToken.collectAsState(
        initial = ""
    ).value ?: ""

    Surface(
        color = colorResource(id = R.color.fondo),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier
        ){
            Spacer(modifier = Modifier.height(50.dp))
            TextoBold(texto = "Escucha lo que quieras", color = colorResource(id = R.color.texto), tamano = 35, modifier = Modifier)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                TextoSemiBold(texto = "Hazlo sonar.", color = colorResource(id = R.color.moradoCool), tamano = 25, modifier = Modifier)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Formulario(nombre = "Nombre de la cancion", icono = painterResource(id = R.drawable.notamusical), onTextSelected = {
                    menuInicioViewModel.onEvent(MenuInicioUIEvent.nombreCancionCambio(it))
                }, hayError = menuInicioViewModel.menuInicioUIState.value.nombreCancionError)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Formulario(nombre = "Artista", icono = painterResource(id = R.drawable.artista), onTextSelected = {
                    menuInicioViewModel.onEvent(MenuInicioUIEvent.artistaCambio(it))
                }, hayError = menuInicioViewModel.menuInicioUIState.value.artistaError)
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Row(
                    modifier = Modifier.width(100.dp)
                ){
                    Boton(nombre = "Buscar", cuandoLoPulsen = {
                        menuInicioViewModel.buscarCancion(token)
                    })
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            if(menuInicioViewModel.menuInicioUIState.value.cancionDisponible){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    TextoBold(texto = menuInicioViewModel.menuInicioUIState.value.cancionActual, color = colorResource(
                        id = R.color.texto
                    ), tamano = 30, modifier = Modifier)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    TextoSemiBold(texto = menuInicioViewModel.menuInicioUIState.value.artistaActual, color = colorResource(
                        id = R.color.moradoCool
                    ), tamano = 15, modifier = Modifier)
                }
                ReproductorMusica(
                    mediaPlayer = menuInicioViewModel.mediaPlayer,
                    isPlaying = menuInicioViewModel.isPlaying.value,
                    onTogglePlay = {
                        menuInicioViewModel.togglePlay(menuInicioViewModel.menuInicioUIState.value.rutaDelCelularDeCancion)
                    }
                )
            }else{
                LoCool()
            }
        }
    }
}

@Composable
fun ReproductorMusica(
    mediaPlayer: MediaPlayer?,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { onTogglePlay() }
        ) {
            Icon(
                painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Preview
@Composable
fun VistaPreviaDeMenuInicio(){
    MenuInicioPantalla()
}