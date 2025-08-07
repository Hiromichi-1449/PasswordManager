package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.passwordmanager.ui.pages.MainScreen
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager.ui.pages.ImportScreen
import com.example.passwordmanager.ui.pages.Routes


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PasswordManagerTheme {
                NavHost(navController = navController, startDestination = Routes.mainS, builder = {
                    composable(Routes.mainS) {
                        MainScreen(navController = navController)
                    }
                    composable(Routes.importS) {
                        ImportScreen()
                    }
                }  )
            }
        }
    }
}