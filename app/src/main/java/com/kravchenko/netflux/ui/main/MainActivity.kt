package com.kravchenko.netflux.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kravchenko.netflux.ui.navigation.NavigationScreen
import com.kravchenko.netflux.ui.theme.netfluxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            netfluxTheme {
                NavigationScreen()
            }
        }
    }
}