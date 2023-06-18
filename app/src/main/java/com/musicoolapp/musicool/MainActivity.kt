package com.musicoolapp.musicool

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.musicoolapp.musicool.app.MusicoolApp
import com.musicoolapp.musicool.red.Red
import com.musicoolapp.musicool.ui.theme.MusicoolTheme

val Context.dataStore by preferencesDataStore(name = "SESION")

class MainActivity : ComponentActivity() {

    private val red by lazy {
        Red(getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicoolTheme {
                MusicoolApp()
            }
        }
    }
}
