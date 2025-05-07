package de.tho.cmp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.tho.Task

@Composable
fun ToDoScreen(navController: NavController) {

    val tasks = rememberSaveable { mutableStateListOf(Task("Task 1"), Task("2")) }

    var newTaskString by remember { mutableStateOf("") }

    fun addTask() {
        if(newTaskString.isNotBlank()) {
            tasks.add(Task(newTaskString))
            newTaskString = ""
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                placeholder = { Text("Something you want to do...") },
                modifier = Modifier.weight(1f).fillMaxHeight().padding(end = 8.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = { addTask() }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                value = newTaskString, onValueChange = {newTaskString = it}
            )
            Button(
                modifier = Modifier.padding(0.dp).fillMaxHeight().pointerHoverIcon(PointerIcon.Hand),
                shape = RoundedCornerShape(8.dp),
                onClick = { addTask() },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text("Add Task")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(shape = RoundedCornerShape(8.dp), border = BorderStroke(width = 2.dp, color = Color.Gray))
            ,
        ) {
            itemsIndexed(tasks) { index, task ->
                TaskRow(task, index % 2 == 0, {tasks.remove(task)})
            }
        }

    }
}

@Composable
fun TaskRow(task: Task, even: Boolean, onRemove: (Task) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(if (even) WTDColor.LIGHTPURPLE.color else WTDColor.LIGHTERPURPLE.color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = task.description,
        )
        RadioButton(
            modifier = Modifier
                .padding(end=10.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            selected = false,
            onClick = {
                onRemove(task)
            }
        )
    }
}