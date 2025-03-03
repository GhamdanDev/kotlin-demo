package com.ghamdandev.demo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            label = { Text("Posts") },  // Changed from Users
            selected = navController.currentDestination?.route == "posts",
            onClick = { navController.navigate("posts") },
            icon = { Icon(Icons.Default.List, contentDescription = "Posts") }
        )
        NavigationBarItem(
            label = { Text("Settings") },
            selected = navController.currentDestination?.route == "settings",
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )

        NavigationBarItem(
            label = { Text("Create Post") },
            selected = navController.currentDestination?.route == "createPost",
            onClick = { navController.navigate("createPost") },
            icon = { Icon(Icons.Default.Add, contentDescription = "Create Post") }
        )
    }
}