package com.ghamdandev.demo.ui.screens.posts

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ghamdandev.demo.data.api.model.Post

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun EditableTableRow(
    post: Post,
    onPostUpdated: (Post) -> Unit,
    onPostDeleted: (Long) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editableUserId by remember { mutableStateOf(post.userId.toString()) }
    var editableTitle by remember { mutableStateOf(post.title) }
    var editableBody by remember { mutableStateOf(post.body) }
    val elevationState by animateDpAsState(
        targetValue = if (isEditing) 4.dp else 0.dp,
        animationSpec = tween(300)
    )
    val backgroundColorState by animateColorAsState(
        targetValue = if (isEditing)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        else
            MaterialTheme.colorScheme.surface
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .shadow(elevationState, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColorState)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ID (Not Editable)
                Text(
                    text = "#${post.id}",
                    modifier = Modifier.weight(0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                // User ID
                if (isEditing) {
                    OutlinedTextField(
                        value = editableUserId,
                        onValueChange = { editableUserId = it },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(horizontal = 4.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
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
                    OutlinedTextField(
                        value = editableTitle,
                        onValueChange = { editableTitle = it },
                        modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = 4.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )
                } else {
                    Text(
                        text = post.title,
                        modifier = Modifier.weight(2f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Body
                if (isEditing) {
                    OutlinedTextField(
                        value = editableBody,
                        onValueChange = { editableBody = it },
                        modifier = Modifier
                            .weight(2.5f)
                            .padding(horizontal = 4.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )

                    // Action buttons
                    Row(
                        modifier = Modifier.weight(0.5f),
                        horizontalArrangement = Arrangement.End
                    ) {

                        IconButton(
                            onClick = {
                                val updatedPost = post.copy(
                                    userId = editableUserId.toIntOrNull()?.toLong() ?: post.userId,
                                    title = editableTitle,
                                    body = editableBody
                                )
                                onPostUpdated(updatedPost)
                                isEditing = false
                            }
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }


                    }
                } else {
                    Text(
                        text = post.body,
                        modifier = Modifier.weight(2.5f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )

                    // Edit button
                    Column (
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
//                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(
                            onClick = { isEditing = true }
                        ) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        }

                        IconButton(
                            onClick = {
                                onPostDeleted(post.id)
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}