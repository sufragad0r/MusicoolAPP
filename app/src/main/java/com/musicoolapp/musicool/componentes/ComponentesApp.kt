package com.musicoolapp.musicool.componentes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicoolapp.musicool.R

@Composable
fun TextoBold(texto:String, color:Color, tamano: Int, modifier: Modifier){
    Text(
        text = texto,
        color = color,
        modifier = modifier,
        style = TextStyle(
            fontSize = tamano.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
    )
}

@Composable
fun TextoSemiBold(texto:String, color:Color, tamano: Int, modifier: Modifier){
    Text(
        text = texto,
        color = color,
        modifier = modifier,
        style = TextStyle(
            fontSize = tamano.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal
        )
    )
}

@Composable
fun TextoNormal(texto:String, color:Color, tamano: Int, modifier: Modifier){
    Text(
        text = texto,
        color = color,
        modifier = modifier,
        style = TextStyle(
            fontSize = tamano.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
    )
}

@Composable
fun TextoMedio(texto:String, color:Color, tamano: Int, modifier: Modifier){
    Text(
        text = texto,
        color = color,
        modifier = modifier,
        style = TextStyle(
            fontSize = tamano.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(nombre: String, icono: Painter,
               onTextSelected: (String) -> Unit,
               hayError: Boolean = false
){
    val texto = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        label = { TextoMedio(texto = nombre, color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = colorResource(id = R.color.texto),
            cursorColor = colorResource(id = R.color.moradoCool),
            containerColor = colorResource(id = R.color.formulario),
            errorBorderColor = colorResource(id = R.color.rojoCool)
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = texto.value,
        onValueChange = {
            texto.value = it
            onTextSelected(it) },
modifier = Modifier
    .height(69.dp)
    .width(300.dp)
    .clip(RoundedCornerShape(5.dp)),
leadingIcon = {
    Icon(painter = icono, contentDescription = "")
}, isError = hayError
)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioContrasena(nombre: String, icono: Painter,
                         onTextSelected: (String) -> Unit,
                         hayError: Boolean = false){
    val localFocusManager = LocalFocusManager.current
    val contrasena = remember {
        mutableStateOf("")
    }
    val verContrasena = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        label = { TextoMedio(texto = nombre, color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = colorResource(id = R.color.texto),
            cursorColor = colorResource(id = R.color.moradoCool),
            containerColor = colorResource(id = R.color.formulario),
            errorBorderColor = colorResource(id = R.color.rojoCool),
            errorTrailingIconColor = colorResource(id = R.color.rojoCool)
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = contrasena.value,
        onValueChange = {
            contrasena.value = it
            onTextSelected(it)
        },
        modifier = Modifier
            .height(69.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(5.dp)),
        leadingIcon = {
            Icon(painter = icono, contentDescription = "")
        }, isError = hayError,
        trailingIcon = {
            val iconoActivo = if(verContrasena.value){
                painterResource(id = R.drawable.ojo)
            }else{
                painterResource(id = R.drawable.visionnula)
            }

            var descripcion = if(verContrasena.value){
                stringResource(id = R.string.esconderContrasena)
            }else{
                stringResource(id = R.string.mostrarContrasena)
            }
            
            IconButton(onClick = { verContrasena.value = !verContrasena.value}) {
                Icon(painter = iconoActivo, contentDescription = descripcion)
            }
        },
        visualTransformation = if(verContrasena.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun TextoPresionable(texto: String, textoResaltado: String, enTextoSeleccionado: (String) -> Unit){


    val annotatedString = buildAnnotatedString {
        append(texto)
        withStyle(style = SpanStyle(color = colorResource(id = R.color.moradoCool), fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = textoResaltado, annotation = textoResaltado)
            append(textoResaltado)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->

        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                if (span.item == textoResaltado) {
                    enTextoSeleccionado(textoResaltado)
                }
            }

    })
}

@Composable
fun Boton(nombre: String, cuandoLoPulsen: () -> Unit, estaActivo: Boolean = true) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            cuandoLoPulsen.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = estaActivo
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            colorResource(
                                id = R.color.moradoCool
                            ),
                            colorResource(
                                id = R.color.moradorSuperCool
                            )
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nombre,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun Musicool(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center){
        TextoBold(texto = stringResource(id = R.string.m_musicool), color = colorResource(id = R.color.moradoCool), tamano = 90, modifier = Modifier
            .alignByBaseline()
            .heightIn(108.dp))
        TextoNormal(texto = stringResource(id = R.string.usicool_musicool), color = colorResource(id = R.color.texto), tamano = 55, modifier = Modifier
            .alignByBaseline()
            .heightIn(67.dp)
            .align(Alignment.CenterVertically))
    }
}

@Composable
fun LoCool(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextoSemiBold(texto = stringResource(id = R.string.lo_slogan), color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier.alignByBaseline())
        TextoSemiBold(texto = stringResource(id = R.string.cool_slogan), color = colorResource(id = R.color.moradoCool), tamano = 15, modifier = Modifier.alignByBaseline())
        TextoSemiBold(texto = stringResource(id = R.string.de_slogan), color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier.alignByBaseline())
        TextoSemiBold(texto = stringResource(id = R.string.sonar_slogan), color = colorResource(id = R.color.moradoCool), tamano = 15, modifier = Modifier.alignByBaseline())
        TextoSemiBold(texto = stringResource(id = R.string.punto_slogan), color = colorResource(id = R.color.texto), tamano = 15, modifier = Modifier.alignByBaseline())
    }
}