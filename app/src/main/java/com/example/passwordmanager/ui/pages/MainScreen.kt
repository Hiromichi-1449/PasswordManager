package com.example.passwordmanager.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.NaviBarItem
import com.example.passwordmanager.R

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {

    val natItemList = listOf(
        NaviBarItem("Home", Icons.Default.Home),
        NaviBarItem("Add", Icons.Default.Add),
        NaviBarItem("Account", Icons.Default.AccountCircle)
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                natItemList.forEachIndexed { index, navBarItem ->
                   NavigationBarItem(
                       selected = true,
                       onClick = {},
                       icon = {
                           Icon(imageVector = navBarItem.icon, contentDescription = "Icon")
                       },
                       label = {
                           Text(text = navBarItem.label)
                       }
                   )
                }
            }
        }

    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(
            onClick = {navController.navigate(Routes.importS)},
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFF89B0AE),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
            ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_import),
                    contentDescription = "Import Icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Import your accounts")
            }

        }

    }
}