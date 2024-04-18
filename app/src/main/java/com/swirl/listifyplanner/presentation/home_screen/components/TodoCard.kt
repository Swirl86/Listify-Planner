package com.swirl.listifyplanner.presentation.home_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.common.taskTextStyle

@Composable
fun TodoCard(
    todo: Todo,
    onDelete: () -> Unit,
    onUpdate: (id: Int) -> Unit
) {
    var done by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    done = !done
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    tint = if (done) Color.Green else Color.Gray,
                    contentDescription = null
                )
            }
            ClickableText(
                text = AnnotatedString(todo.task),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(8f),
                style = taskTextStyle(done),
                onClick = {
                    done = !done
                },
            )
            if (todo.isImportant) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = Color.Cyan,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    tint = Color.Red,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { onUpdate(todo.id) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null
                )
            }
        }
    }
}