import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color

import com.ghamdandev.demo.ui.screens.posts.PostViewModel

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.filled.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.shadow

import com.ghamdandev.demo.ui.screens.posts.AddPostDialog
import com.ghamdandev.demo.ui.screens.posts.PostTable
import kotlinx.coroutines.launch

@Composable
fun PostsScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val operationStatus by viewModel.operationStatus.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val customColors = MaterialTheme.colorScheme.copy(
        primary = Color(0xFF1E88E5),
        primaryContainer = Color(0xFFE3F2FD),
        secondary = Color(0xFF00897B),
        surface = Color(0xFFFFFFFF),
        background = Color(0xFFF5F5F5),
        error = Color(0xFFD32F2F)
    )

    LaunchedEffect(operationStatus) {
        viewModel.fetchPosts()
        operationStatus?.let {
            val message = if (it) "Post added successfully!" else "Failed to add post"
            snackbarHostState.showSnackbar(message)
            viewModel.clearOperationStatus()
        }
    }

    MaterialTheme(colorScheme = customColors) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp)),
                        containerColor = if (operationStatus == true) Color(0xFF4CAF50) else Color(0xFFD32F2F),
                        contentColor = Color.White,
                        action = {
                            TextButton(
                                onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                            ) {
                                Text("DISMISS", color = Color.White)
                            }
                        }
                    ) {
                        Text(data.visuals.message)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    shape = CircleShape,
                    containerColor = customColors.primary,
                    contentColor = Color.White,
                    modifier = Modifier.shadow(
                        elevation = 6.dp,
                        shape = CircleShape,
                        spotColor = customColors.primary.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Post",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            containerColor = customColors.background
        ) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = customColors.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Loading posts...",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                    error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Error",
                                    tint = customColors.error,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Error: ${error ?: "Unknown"}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = customColors.error
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.fetchPosts() },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Retry")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    posts.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "No Posts",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "No posts available",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { showAddDialog = true },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Add New Post")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Add Your First Post")
                                }
                            }
                        }
                    }
                    else -> {
                        PostTable(
                            posts = posts,
                            onPostUpdated = { updatedPost ->
                                viewModel.updatePost(updatedPost)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Post updated successfully!")
                                }
                            },
                            onPostDeleted = { postId ->
                                viewModel.deletePost(postId)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Post deleted successfully!")
                                }
                            }
                        )
                    }
                }

                if (showAddDialog) {
                    AddPostDialog(
                        onDismiss = { showAddDialog = false },
                        onConfirm = { newPost ->
                            viewModel.addPost(newPost)
                            showAddDialog = false
                        }
                    )
                }
            }
        }
    }
}







