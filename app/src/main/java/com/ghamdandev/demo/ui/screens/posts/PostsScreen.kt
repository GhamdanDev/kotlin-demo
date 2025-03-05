import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghamdandev.demo.data.api.model.Post
//import com.ghamdandev.demo.ui.screens.posts.PostViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build

import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color

import com.ghamdandev.demo.ui.screens.posts.PostViewModel



@Composable
fun PostsScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val operationStatus by viewModel.operationStatus.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) } // التحكم في عرض النافذة

    // إظهار Snackbar عند نجاح أو فشل العملية
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(operationStatus) {
        if (operationStatus == true) {
            snackbarHostState.showSnackbar("Post added successfully!")
            viewModel.clearOperationStatus()
        } else if (operationStatus == false) {
            snackbarHostState.showSnackbar("Failed to add post")
            viewModel.clearOperationStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Post")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when {
                isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                error != null -> Text(
                    "Error: ${error ?: "Unknown"}",
                    Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
                posts.isEmpty() -> Text("No posts available", modifier = Modifier.align(Alignment.Center))
                else -> PostTable(
                    posts = posts,
                    onPostUpdated = { updatedPost -> viewModel.updatePost(updatedPost) },
                    onPostDeleted = { postId -> viewModel.deletePost(postId) }
                )
            }

            // عرض نافذة إضافة منشور
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
@Composable
fun AddPostDialog(
    onDismiss: () -> Unit, // إغلاق النافذة
    onConfirm: (Post) -> Unit // تأكيد الإضافة
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var bodyError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Post") },
        text = {
            Column {
                // حقل إدخال العنوان
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    isError = titleError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (titleError) {
                    Text(
                        text = "Title is required",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // حقل إدخال المحتوى
                TextField(
                    value = body,
                    onValueChange = {
                        body = it
                        bodyError = it.isBlank()
                    },
                    label = { Text("Body") },
                    isError = bodyError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (bodyError) {
                    Text(
                        text = "Body is required",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    titleError = title.isBlank()
                    bodyError = body.isBlank()
                    if (!titleError && !bodyError) {
                        val newPost = Post(
                            id = System.currentTimeMillis(), // استخدام الوقت كمثال لـ ID
                            userId = 1, // يمكن تغيير هذا ليكون ديناميكيًا
                            title = title,
                            body = body
                        )
                        onConfirm(newPost)
                        onDismiss()
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun PostTable(
    posts: List<Post>,
    onPostUpdated: (Post) -> Unit,
    onPostDeleted: (Long) -> Unit // دالة الحذف
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortColumn by remember { mutableStateOf(SortColumn.ID) }
    var sortOrder by remember { mutableStateOf(SortOrder.ASC) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search by ID, User ID, Title, or Body") }
        )

        // Process Posts
        val filteredPosts = remember(posts, searchQuery) {
            if (searchQuery.isEmpty()) posts else posts.filter {
                it.id.toString().contains(searchQuery, true) ||
                        it.userId.toString().contains(searchQuery, true) ||
                        it.title.contains(searchQuery, true) ||
                        it.body.contains(searchQuery, true)
            }
        }
        val sortedPosts = remember(filteredPosts, sortColumn, sortOrder) {
            val comparator = when (sortColumn) {
                SortColumn.ID -> compareBy<Post> { it.id }
                SortColumn.USER_ID -> compareBy<Post> { it.userId }
                SortColumn.TITLE -> compareBy<Post> { it.title }
                SortColumn.BODY -> compareBy<Post> { it.body }
            }

            if (sortOrder == SortOrder.DESC) {
                filteredPosts.sortedWith(comparator.reversed())
            } else {
                filteredPosts.sortedWith(comparator)
            }
        }

        // Table Header
        TableHeader(
            sortColumn = sortColumn,
            sortOrder = sortOrder,
            onSortChange = { column ->
                if (sortColumn == column) {
                    sortOrder = if (sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
                } else {
                    sortColumn = column
                    sortOrder = SortOrder.ASC
                }
            }
        )

        // Table Rows
        LazyColumn {
            items(sortedPosts) { post ->
                EditableTableRow(
                    post = post,
                    onPostUpdated = onPostUpdated,
                    onPostDeleted = onPostDeleted // تمرير دالة الحذف
                )
            }
        }
    }
}

@Composable
fun TableHeader(
    sortColumn: SortColumn,
    sortOrder: SortOrder,
    onSortChange: (SortColumn) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            HeaderCell("ID", SortColumn.ID, sortColumn, sortOrder, onSortChange, 0.5f)
            HeaderCell("User ID", SortColumn.USER_ID, sortColumn, sortOrder, onSortChange, 0.5f)
            HeaderCell("Title", SortColumn.TITLE, sortColumn, sortOrder, onSortChange, 2f)
            HeaderCell("Body", SortColumn.BODY, sortColumn, sortOrder, onSortChange, 3f)
        }
    }
}

@Composable
private fun HeaderCell(
    title: String,
    column: SortColumn,
    currentSortColumn: SortColumn,
    sortOrder: SortOrder,
    onSortChange: (SortColumn) -> Unit,
    weight: Float
) {
    Row(
        modifier = Modifier

            .clickable { onSortChange(column) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        if (currentSortColumn == column) {
            Icon(
                imageVector = if (sortOrder == SortOrder.ASC) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Sort",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun EditableTableRow(
    post: Post,
    onPostUpdated: (Post) -> Unit,
    onPostDeleted: (Long) -> Unit // دالة الحذف
) {
    var isEditing by remember { mutableStateOf(false) }
    var editableUserId by remember { mutableStateOf(post.userId.toString()) }
    var editableTitle by remember { mutableStateOf(post.title) }
    var editableBody by remember { mutableStateOf(post.body) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isEditing = !isEditing }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ID (Not Editable)
            Text(
                text = post.id.toString(),
                modifier = Modifier.weight(0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // User ID
            if (isEditing) {
                TextField(
                    value = editableUserId,
                    onValueChange = { editableUserId = it },
                    modifier = Modifier.weight(0.5f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            } else {
                Text(
                    text = post.userId.toString(),
                    modifier = Modifier.weight(0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Title
            if (isEditing) {
                TextField(
                    value = editableTitle,
                    onValueChange = { editableTitle = it },
                    modifier = Modifier.weight(2f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            } else {
                Text(
                    text = post.title,
                    modifier = Modifier.weight(2f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Body
            if (isEditing) {
                TextField(
                    value = editableBody,
                    onValueChange = { editableBody = it },
                    modifier = Modifier.weight(2.5f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                IconButton(
                    onClick = {
                        val updatedPost = post.copy(
                            userId = editableUserId.toIntOrNull()?.toLong() ?: post.userId,
                            title = editableTitle,
                            body = editableBody
                        )
                        onPostUpdated(updatedPost)
                        isEditing = false
                    },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
                IconButton(
                    onClick = {
                        onPostDeleted(post.id) // استدعاء دالة الحذف
                        isEditing = false
                    },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            } else {
                Text(
                    text = post.body,
                    modifier = Modifier.weight(3f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

enum class SortColumn { ID, USER_ID, TITLE, BODY }
enum class SortOrder { ASC, DESC }
//*/
/* this done display post and edit it   */