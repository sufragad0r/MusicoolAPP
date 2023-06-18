package com.musicoolapp.musicool.red

import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MusicoolAPI {

    val BASE_URL = "http://192.168.1.161:8000/"

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

                    // Imprimir los datos del objeto
                    Log.d("INICIO SESION API", "solicitarOTP: ${responseData.solicitarOTP}")
                    Log.d("INICIO SESION API", "info: ${responseData.info}")

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


}