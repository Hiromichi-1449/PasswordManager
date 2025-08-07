package com.example.passwordmanager.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ImportScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ImportContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ImportContent(modifier: Modifier = Modifier) {
    Column() {
        Text("Select your source of import")
    }
}