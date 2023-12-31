package com.musicoolapp.musicool.red

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.Base64
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class MusicoolAPI {

    val BASE_URL = "http://192.168.43.130:8000/"
    val BASIC_AUTH_KEY = "clienteMovil"
    val BASIC_AUTH_VALUE ="fR5^hN7*oP#2"

    fun iniciarSesion(nombreUsuario:String, contrasena: String,callback: (SolicitarOTP?) -> Unit) {
        Thread(Runnable {
            try {
                val connection = URL(BASE_URL + "login").openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
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
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
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
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
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

     fun buscarImagen(token: String, id: String, callback: (ImageBitmap?) -> Unit) {
        Thread {
            try {
                val url = BASE_URL + "buscar-imagen"
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
                    CoroutineScope(Dispatchers.IO).launch {
                        val imageBitmap = decodeByteArrayToBitmap(imageData)
                        withContext(Dispatchers.Main) {
                            callback(imageBitmap)
                        }
                    }
                    println("Imagen encontrada")
                } else {
                    callback(null)
                    println("No se pudo obtener la imagen")
                }

                connection.disconnect()
            } catch (e: Exception) {
                println("Error en la solicitud: ${e.message}")
                callback(null)
            }
        }.start()
    }

    suspend fun decodeByteArrayToBitmap(byteArray: ByteArray): ImageBitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                bitmap.asImageBitmap()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    fun obtenerCancion(token: String, id: String?, callback: (File?) -> Unit) {
        Thread {
            try {
                val url = BASE_URL + "obtener-cancion"
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
                    val file = guardarArchivoMP3(inputStream, id.toString())
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

    fun guardarArchivoMP3(inputStream: InputStream, fileName: String): File? {
        val externalStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != externalStorageState) {
            // El almacenamiento externo no está montado o no está disponible
            return null
        }

        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        val file = File(downloadsFolder, fileName+".mp3")

        return try {
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.flush()
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun buscarCancion(token: String, cancion: String, artista: String, callback: (Cancion?) -> Unit) {
        Thread {
            try {
                val url = BASE_URL + "buscar-cancion"
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

                    val jsonObject = JSONObject(response)
                    val id = jsonObject.getString("id")
                    val nombre = jsonObject.getString("nombre")
                    val artista = jsonObject.getString("artista")
                    val fechaDePublicacion = jsonObject.getString("fechaDePublicacion")
                    val cancionObjeto = Cancion(id, nombre, artista, fechaDePublicacion)

                    callback(cancionObjeto)
                    Log.d("ID CANCION", "El id de la canción es : $id")
                } else {
                    callback(null)
                    Log.e("BUSCAR CANCION", "No se encontró una canción")
                }
                connection.disconnect()
            } catch (e: Exception) {
                Log.e("BUSCAR CANCION", "Error en la solicitud: ${e.message}")
                callback(null)
            }
        }.start()
    }




    data class Usuario(
        val username: String,
        val password: String,
        val telefono: String
        )
    data class Cancion(
        val id: String,
        val nombre: String,
        val artista: String,
        val fechaDePublicacion: String
    )



}