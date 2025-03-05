

package com.ghamdandev.demo
import PostsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghamdandev.demo.ui.screens.posts.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: PostViewModel = viewModel()
            PostsScreen(viewModel)
        }
    }
}
