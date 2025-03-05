package com.ghamdandev.demo.ui.screens.posts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp



    @Composable
    fun TableHeader(
        sortColumn: SortColumn,
        sortOrder: SortOrder,
        onSortChange: (SortColumn) -> Unit
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderCell("ID", SortColumn.ID, sortColumn, sortOrder, onSortChange, 0.5f)
                HeaderCell("User ID", SortColumn.USER_ID, sortColumn, sortOrder, onSortChange, 0.5f)
                HeaderCell("Title", SortColumn.TITLE, sortColumn, sortOrder, onSortChange, 2f)
                HeaderCell("Body", SortColumn.BODY, sortColumn, sortOrder, onSortChange, 3f)
                // Empty space for action buttons
                Spacer(modifier = Modifier.weight(0.5f))
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
        val interactionSource = remember { MutableInteractionSource() }
        val isSorted = currentSortColumn == column
        val backgroundColor by animateColorAsState(
            targetValue = if (isSorted)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                Color.Transparent
        )

        Box(
            modifier = Modifier

                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onSortChange(column)
                }
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = if (isSorted) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSorted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (isSorted) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (sortOrder == SortOrder.ASC)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
