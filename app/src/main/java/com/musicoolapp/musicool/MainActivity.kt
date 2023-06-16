package com.musicoolapp.musicool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.musicoolapp.musicool.app.MusicoolApp
import com.musicoolapp.musicool.ui.theme.MusicoolTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicoolTheme {
                MusicoolApp()
            }
        }
    }
}
