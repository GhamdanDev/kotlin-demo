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
import androidx.compose.material.icons.filled.Build

import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


import androidx.hilt.navigation.compose.hiltViewModel
import com.ghamdandev.demo.ui.screens.posts.PostViewModel
//
//@Composable
//fun PostsScreen(viewModel: PostViewModel = viewModel()) {
//    val posts by viewModel.posts.collectAsState()
//    val operationStatus by viewModel.operationStatus.collectAsState()
//
//    Scaffold { paddingValues ->
//        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
//            if (posts.isEmpty()) {
//                Text("No posts available", modifier = Modifier.align(Alignment.Center))
//            } else {
//                PostTable(
//                    posts = posts,
//                    onPostUpdated = { updatedPost -> viewModel.updatePost(updatedPost) },
//                    onPostDeleted = { postId -> viewModel.deletePost(postId) }
//                )
//            }
//
//            // عرض حالة العملية (نجاح/فشل)
//            operationStatus?.let { status ->
//                Text(
//                    if (status) "Operation Successful" else "Operation Failed",
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(16.dp)
//                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
//                        .padding(8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PostTable(
//    posts: List<Post>,
//    onPostUpdated: (Post) -> Unit,
//    onPostDeleted: (Long) -> Unit
//) {
//    var searchQuery by remember { mutableStateOf("") }
//    var sortColumn by remember { mutableStateOf(SortColumn.ID) }
//    var sortOrder by remember { mutableStateOf(SortOrder.ASC) }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // شريط البحث
//        TextField(
//            value = searchQuery,
//            onValueChange = { searchQuery = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            placeholder = { Text("Search by ID, User ID, Title, or Body") }
//        )
//
//        // معالجة المنشورات
//        val filteredPosts = remember(posts, searchQuery) {
//            if (searchQuery.isEmpty()) posts else posts.filter {
//                it.id.toString().contains(searchQuery, true) ||
//                        it.userId.toString().contains(searchQuery, true) ||
//                        it.title.contains(searchQuery, true) ||
//                        it.body.contains(searchQuery, true)
//            }
//        }
//        val sortedPosts = remember(filteredPosts, sortColumn, sortOrder) {
//            val comparator = when (sortColumn) {
//                SortColumn.ID -> compareBy<Post> { it.id }
//                SortColumn.USER_ID -> compareBy<Post> { it.userId }
//                SortColumn.TITLE -> compareBy<Post> { it.title }
//                SortColumn.BODY -> compareBy<Post> { it.body }
//            }
//
//            if (sortOrder == SortOrder.DESC) {
//                filteredPosts.sortedWith(comparator.reversed())
//            } else {
//                filteredPosts.sortedWith(comparator)
//            }
//        }
//
//        // رأس الجدول
//        TableHeader(
//            sortColumn = sortColumn,
//            sortOrder = sortOrder,
//            onSortChange = { column ->
//                if (sortColumn == column) {
//                    sortOrder = if (sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
//                } else {
//                    sortColumn = column
//                    sortOrder = SortOrder.ASC
//                }
//            }
//        )
//
//        // صفوف الجدول
//        LazyColumn {
//            items(sortedPosts) { post ->
//                EditableTableRow(
//                    post = post,
//                    onPostUpdated = onPostUpdated,
//                    onPostDeleted = onPostDeleted
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun EditableTableRow(
//    post: Post,
//    onPostUpdated: (Post) -> Unit,
//    onPostDeleted: (Long) -> Unit
//) {
//    var isEditing by remember { mutableStateOf(false) }
//    var editableUserId by remember { mutableStateOf(post.userId.toString()) }
//    var editableTitle by remember { mutableStateOf(post.title) }
//    var editableBody by remember { mutableStateOf(post.body) }
//
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 2.dp),
//        color = MaterialTheme.colorScheme.surface
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { isEditing = !isEditing }
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // ID (غير قابل للتعديل)
//            Text(
//                text = post.id.toString(),
//                modifier = Modifier.weight(0.5f),
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            // User ID
//            if (isEditing) {
//                TextField(
//                    value = editableUserId,
//                    onValueChange = { editableUserId = it },
//                    modifier = Modifier.weight(0.5f),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedContainerColor = Color.Transparent
//                    )
//                )
//            } else {
//                Text(
//                    text = post.userId.toString(),
//                    modifier = Modifier.weight(0.5f),
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            // Title
//            if (isEditing) {
//                TextField(
//                    value = editableTitle,
//                    onValueChange = { editableTitle = it },
//                    modifier = Modifier.weight(2f),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedContainerColor = Color.Transparent
//                    )
//                )
//            } else {
//                Text(
//                    text = post.title,
//                    modifier = Modifier.weight(2f),
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            // Body
//            if (isEditing) {
//                TextField(
//                    value = editableBody,
//                    onValueChange = { editableBody = it },
//                    modifier = Modifier.weight(2.5f),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedContainerColor = Color.Transparent
//                    )
//                )
//                IconButton(
//                    onClick = {
//                        val updatedPost = post.copy(
//                            userId = editableUserId.toIntOrNull() ?: post.userId,
//                            title = editableTitle,
//                            body = editableBody
//                        )
//                        onPostUpdated(updatedPost)
//                        isEditing = false
//                    },
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Icon(Icons.Default.Check, contentDescription = "Save")
//                }
//            } else {
//                Text(
//                    text = post.body,
//                    modifier = Modifier.weight(3f),
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//                IconButton(
//                    onClick = { onPostDeleted(post.id) },
//                    modifier = Modifier.weight(0.5f)
//                ) {
//                    Icon(Icons.Default.Delete, contentDescription = "Delete")
//                }
//            }
//        }
//    }
//}

//@Composable
//fun PostsScreen(
//    viewModel: PostViewModel = hiltViewModel() // تغيير هنا
//) {
////@Composable
////fun PostsScreen(viewModel: PostViewModel = viewModel()) {
//    val posts by viewModel.posts.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.error.collectAsState()
//
//    Scaffold(
//    ) { paddingValues ->
//        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
//            when {
//                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                error != null -> Text(text = "Error: $error", modifier = Modifier.align(Alignment.Center))
//                posts.isEmpty() -> Text("No posts available", modifier = Modifier.align(Alignment.Center))
//                else -> PostList(posts)
//            }
//        }
//    }
//}
//
//@Composable
//fun PostList(posts: List<Post>) {
//    LazyColumn {
//        items(posts) { post ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//
//                ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(text = post.title, style = MaterialTheme.typography.titleLarge)
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(text = post.body)
//                }
//            }
//        }
//    }
//}

/////*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val operationStatus by viewModel.operationStatus.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // معالجة عرض رسائل النجاح والخطأ
    LaunchedEffect(error, operationStatus) {
        error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            viewModel.clearError()
        }
        operationStatus?.let {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Operation completed successfully",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
                viewModel.clearOperationStatus()
            }
        }
    }

    // جلب البيانات عند بدء الشاشة
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Posts Management") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.fetchPosts() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Retry")
                        }
                    }
                }
                posts.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "No Posts",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No posts available",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                else -> {
                    PostTable(
                        posts = posts,
                        onPostUpdated = { updatedPost ->
                            viewModel.updatePost(updatedPost)
                        }
                    )
                }
            }
        }
    }
}


//@Composable
//fun PostsScreen(viewModel: PostViewModel = viewModel()) {
//    val posts by viewModel.posts.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.error.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchPosts()
//    }
//
//    Scaffold { paddingValues ->
//        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
//            when {
//                isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
//                error != null -> Text(
//                    "Error: ${error ?: "Unknown"}",
//                    Modifier.align(Alignment.Center).padding(16.dp)
//                )
//                posts.isEmpty() -> Text("No posts available", modifier = Modifier.align(Alignment.Center))
//                else -> PostTable(
//                    posts = posts,
//                    onPostUpdated = { updatedPost -> viewModel.updatePost(updatedPost) }
//                )
//            }
//        }
//    }
//}
//
@Composable
fun PostTable(
    posts: List<Post>,
    onPostUpdated: (Post) -> Unit
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
                    onPostUpdated = onPostUpdated
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
    onPostUpdated: (Post) -> Unit
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