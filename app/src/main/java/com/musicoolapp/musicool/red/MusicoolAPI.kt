package com.musicoolapp.musicool.red

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.Base64

class MusicoolAPI {

    val BASE_URL = "http://192.168.100.11:8000/"
    val BASIC_AUTH_KEY = "clienteMovil"
    val BASIC_AUTH_VALUE ="fR5^hN7*oP#2"

    fun iniciarSesion(nombreUsuario:String, contrasena: String,callback: (SolicitarOTP?) -> Unit) {
        Thread(Runnable {
            try {
                val connection = URL(BASE_URL + "login").openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.setRequestProperty("accept", "application/json")
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val postData = "grant_type=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&username=" +
                        URLEncoder.encode(nombreUsuario, "UTF-8") +
                        "&password=" +
                        URLEncoder.encode(contrasena, "UTF-8") +
                        "&scope=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&client_id=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&client_secret=" +
                        URLEncoder.encode("", "UTF-8")

                connection.doOutput = true
                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.writeBytes(postData)
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

                    val gson = Gson()
                    val responseData = gson.fromJson(response, SolicitarOTP::class.java)


                    Log.d("INICIO SESION API", "solicitarOTP: ${responseData.solicitarOTP}")

                    callback(responseData)
                } else {
                    Log.d("INICIO SESION API", "Error en la solicitud. Código de respuesta: $responseCode")
                    callback(null)
                }

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("INICIO SESION API", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }).start()
    }

    fun codigoOTP(nombreUsuario: String, codigo: String, callback: (Token?) -> Unit){
        Thread(Runnable {
            try {
                val connection = URL(BASE_URL + "login/auth").openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.setRequestProperty("accept", "application/json")
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val postData = "grant_type=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&username=" +
                        URLEncoder.encode(nombreUsuario, "UTF-8") +
                        "&password=" +
                        URLEncoder.encode(codigo, "UTF-8") +
                        "&scope=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&client_id=" +
                        URLEncoder.encode("", "UTF-8") +
                        "&client_secret=" +
                        URLEncoder.encode("", "UTF-8")

                connection.doOutput = true
                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.writeBytes(postData)
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

                    val gson = Gson()
                    val responseData = gson.fromJson(response, Token::class.java)

                    callback(responseData)
                    Log.d("CODIGO OTP API", "Solicitud exitosa")
                } else {
                    Log.d("CODIGO OTP API", "Error en la solicitud. Código de respuesta: $responseCode")
                    callback(null)
                }

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("INICIO SESION API", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }).start()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun registrarUsuario(usuario: Usuario, callback: (Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(BASE_URL + "usuarios")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.setRequestProperty("accept", "application/json")
                connection.setRequestProperty("Content-Type", "application/json")

                val authCredentials = BASIC_AUTH_KEY+":"+BASIC_AUTH_VALUE;
                val encodedCredentials = Base64.getEncoder().encodeToString(authCredentials.toByteArray())
                val authHeader = "Basic $encodedCredentials"
                connection.setRequestProperty("Authorization", authHeader)

                val postData = """
                {
                    "username": "${usuario.username}",
                    "password": "${usuario.password}",
                    "telefono": "${usuario.telefono}",
                    "rol": "escucha"
                }
            """.trimIndent()

                connection.doOutput = true
                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.writeBytes(postData)
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

                    callback(true)
                } else {
                    callback(false)
                }

                connection.disconnect()
            } catch (e: Exception) {
                callback(false)
            }
        }.start()
    }
    data class Usuario(
        val username: String,
        val password: String,
        val telefono: String
        )


}