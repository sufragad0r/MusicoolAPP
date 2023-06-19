package com.musicoolapp.musicool.red
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.Base64
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MusicoolAPI {

    val BASE_URL = "http://192.168.1.161:8000/"
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

    fun buscarImagen(token: String, id: String, callback: (ByteArray?) -> Unit) {
        Thread {
            try {
                val url = BASE_URL+ "buscar-imagen"
                val params = listOf("id" to id)
                val headers = mapOf(
                    "accept" to "application/json",
                    "token" to token
                )

                val connection = URL("$url?id=$id").openConnection() as HttpURLConnection
                connection.setRequestProperty("accept", headers["accept"])
                connection.setRequestProperty("token", headers["token"])

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val outputStream = ByteArrayOutputStream()

                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }

                    val imageData = outputStream.toByteArray()
                    callback(imageData)
                    Log.d("BUSCAR IMAGEN", "Imagen encontrada")
                } else {
                    callback(null)
                    Log.e("BUSCAR IMAGEN", "No se pudo obtener la imagen")
                }

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("BUSCAR IMAGEN", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }.start()
    }
    fun obtenerCancion(token: String, id: String, callback: (File?) -> Unit) {
        Thread {
            try {
                val url = BASE_URL+"obtener-cancion";
                val params = listOf("id" to id)
                val headers = mapOf(
                    "accept" to "audio/mpeg",
                    "token" to token
                )

                val connection = URL("$url?id=$id").openConnection() as HttpURLConnection
                connection.setRequestProperty("accept", headers["accept"])
                connection.setRequestProperty("token", headers["token"])

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val file = guardarArchivoMP3(inputStream, id)
                    callback(file)
                    Log.d("OBTENER CANCION", "Canción descargada y guardada")
                } else {
                    callback(null)
                    Log.e("OBTENER CANCION", "No se pudo obtener la canción")
                }

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("OBTENER CANCION", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }.start()
    }
    fun guardarArchivoMP3(inputStream: java.io.InputStream, id: String): File? {
        val filePath = "/canciones/$id.mp3"
        val file = File(filePath)
        try {
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            Log.e("GUARDAR ARCHIVO", "Se guardo el archivo en : ${file.absolutePath}")

            return file
        } catch (e: Exception) {
            Log.e("GUARDAR ARCHIVO", "Error al guardar el archivo: ${e.message}")
        }
        return null
    }



    fun buscarCancion(token: String, cancion: String, artista: String, callback: (String?) -> Unit) {
        Thread {
            try {
        val url = BASE_URL+"buscar-cancion"
        val acceptHeader = "application/json"

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("accept", acceptHeader)
        connection.setRequestProperty("token", token)
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val requestBody = """
        {
            "id": "lorem",
            "nombre": "$cancion",
            "artista": "$artista",
            "fechaDePublicacion": "lorem"
        }
    """.trimIndent()

        val outputStream = DataOutputStream(connection.outputStream)
        outputStream.writeBytes(requestBody)
        outputStream.flush()
        outputStream.close()

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = bufferedReader.use(BufferedReader::readText)
            bufferedReader.close()
            callback(response)
            Log.d("ID CANCION", "El id de la cancion es : $response")
        } else {
            callback(null)
            Log.e("BUSCAR CANCION", "No se encontro una canción")

        }
        connection.disconnect()
            } catch (e: Exception) {
                Log.e("BUSCAR CANCION", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }.start()
    }



    @Serializable
    data class Song(val id: String, val title: String, val artist: String)
    data class Usuario(
        val username: String,
        val password: String,
        val telefono: String
        )


}