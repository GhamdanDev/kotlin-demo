// MainActivity.kt
package com.ghamdandev.demo

import PostsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ghamdandev.demo.ui.screens.auth.LoginScreen

import com.ghamdandev.demo.ui.screens.posts.PostViewModel

import com.ghamdandev.demo.ui.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("posts") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("posts") {
                        val viewModel: PostViewModel = hiltViewModel()
                        PostsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}