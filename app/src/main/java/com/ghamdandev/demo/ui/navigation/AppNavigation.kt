package com.ghamdandev.demo.ui.theme.ui.navigation

import ClickCounterScreen
import CreatePostScreen
//import CreatePostScreen
//import  PostsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import com.ghamdandev.demo.ui.components.BottomNavigationBar


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { ClickCounterScreen() }
//            composable("posts") { PostsScreen() }  // Changed from UsersScreen
//            composable("settings") { SettingsScreen() }
            composable("createPost") { CreatePostScreen(onPostCreated = { navController.navigate("posts") }) }
        }
    }
}
