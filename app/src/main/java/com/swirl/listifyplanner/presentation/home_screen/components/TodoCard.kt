package com.swirl.listifyplanner.presentation.home_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.presentation.common.taskTextStyle
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.ui.theme.TaskLightYellowBg
import com.swirl.listifyplanner.utils.extenstions.getOutPutString

@Composable
fun TodoCard(
    todo: Todo,
    onDelete: () -> Unit,
    onUpdate: (id: Int) -> Unit,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (todo.isDone) TaskLightGreenBg
        else TaskLightYellowBg, animationSpec = tween(500), label = ""
    )

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Rounded.Check,
                    tint = if (todo.isDone) Color.Green else Color.Gray,
                    contentDescription = null
                )
                Text(
                    text = AnnotatedString(todo.task),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(8f),
                    style = taskTextStyle(todo.isDone)
                )
                if (todo.isImportant) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        tint = Color.Cyan,
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                            .padding(end = 4.dp)
                    )
                }
                IconButton(
                    onClick = { onDelete() },
                    modifier = Modifier.weight(1f)
                        .padding(end = 4.dp)
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
                        .padding(end = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth())
                Text(
                    text = todo.timeStamp.getOutPutString(),
                    modifier = Modifier.padding(4.dp),
                    fontSize = 10.sp
                )
            }
        }
    }
}