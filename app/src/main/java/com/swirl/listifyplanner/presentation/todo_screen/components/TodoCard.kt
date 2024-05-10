package com.swirl.listifyplanner.presentation.todo_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.ui.constants.DefaultIconSize
import com.swirl.listifyplanner.ui.constants.SmallPadding
import com.swirl.listifyplanner.ui.constants.taskTextStyle
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.ui.theme.TaskLightYellowBg
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.dateTimeToString
import com.swirl.listifyplanner.utils.getIconColor
import java.time.LocalDateTime

@Composable
fun TodoCard(
    todo: Todo,
    onDelete: () -> Unit,
    onUpdate: (id: Int) -> Unit,
    onClick: () -> Unit
) {

    val primaryColor = MaterialTheme.colors.primary
    val backgroundColor = if (todo.isDone) TaskLightGreenBg.copy(alpha = 0.5f) else TaskLightYellowBg
    val textColor = if (todo.isDone) primaryColor.copy(alpha = 0.5f) else primaryColor
    val checkIcon = if (todo.isDone) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.weight(1f).size(DefaultIconSize),
                    imageVector = checkIcon,
                    tint = getIconColor(todo.isDone, Color.Black),
                    contentDescription = UiText.StringResource( R.string.icon_check).asString()
                )
                Spacer(modifier = Modifier.padding(end = SmallPadding))
                Text(
                    text = AnnotatedString(todo.task),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(8f),
                    style = taskTextStyle(todo.isDone).copy(color = textColor)
                )
                if (todo.isImportant) {
                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                            .size(DefaultIconSize),
                        imageVector = Icons.Rounded.Star,
                        tint = getIconColor(todo.isDone, Color.Cyan),
                        contentDescription = UiText.StringResource( R.string.icon_star).asString()
                    )
                }
                IconButton(
                    onClick = { onDelete() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(DefaultIconSize),
                        imageVector = Icons.Rounded.Delete,
                        tint = getIconColor(todo.isDone, Color.Red),
                        contentDescription = UiText.StringResource( R.string.icon_delete).asString()
                    )
                }

                IconButton(
                    onClick = { onUpdate(todo.id) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(DefaultIconSize),
                        imageVector = Icons.Rounded.Edit,
                        tint = getIconColor(todo.isDone, Color.Black),
                        contentDescription = UiText.StringResource( R.string.icon_edit).asString()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth())
                Text(
                    text = todo.timeStamp.dateTimeToString(),
                    modifier = Modifier.padding(4.dp),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoCardPreview() {
    TodoCard(
        todo = Todo(0, "Todo for preview", LocalDateTime.now()),
        onDelete = { /*TODO*/ },
        onUpdate = { /*TODO*/ }) {

    }
}