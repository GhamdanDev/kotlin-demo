package com.ghamdandev.demo.ui.screens.posts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ghamdandev.demo.data.api.model.Post
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTable(
    posts: List<Post>,
    onPostUpdated: (Post) -> Unit,
    onPostDeleted: (Long) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortColumn by remember { mutableStateOf(SortColumn.ID) }
    var sortOrder by remember { mutableStateOf(SortOrder.ASC) }
    var searchFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Enhanced search bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .shadow(
                    elevation = animateDpAsState(
                        targetValue = if (searchFocused) 8.dp else 4.dp,
                        animationSpec = tween(200)
                    ).value,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 12.dp)
                )
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by ID, User ID, Title, or Body") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { searchFocused = it.isFocused },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    )
                )
            }
        }

        // Table container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
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

                // Enhanced Table Header
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

                if (sortedPosts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "No Results",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No matching posts found",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    // Enhanced Table Rows
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(
                            items = sortedPosts,
                            key = { it.id }
                        ) { post ->
                            EditableTableRow(
                                post = post,
                                onPostUpdated = onPostUpdated,
                                onPostDeleted = onPostDeleted
                            )
                        }
                    }
                }
            }
        }
    }
}