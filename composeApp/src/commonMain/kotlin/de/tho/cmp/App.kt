package de.tho

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.tho.cmp.TimerScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Task(val description: String)

enum class Screen(val route: String, val title: String) {
    ToDo("todo", "ToDos"),
    Timer("timer", "Timer"),
    Detail("details","Details")
}

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        val currentTitle = when (currentRoute) {
            Screen.ToDo.route -> Screen.ToDo.title
            Screen.Timer.route -> Screen.Timer.title
            Screen.Detail.route -> Screen.Detail.title
            else -> "App"
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentTitle,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                MinimalDropdownMenu(navController)
            }

            Spacer(modifier = Modifier.padding(16.dp))

            NavHost(navController = navController, startDestination = Screen.Timer.route) {

                composable(Screen.ToDo.route) { ToDoScreen(navController) }
                composable(Screen.Timer.route) { TimerScreen(navController) }
                composable(Screen.Detail.route) { Text("Test") }
            }
        }
    }
}

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
                placeholder = { Text("Something you want to do...")},
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
                modifier = Modifier.padding(0.dp).fillMaxHeight(),
                onClick = { addTask() },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text("Click me!")
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
            .background(if (even) Color.Transparent else Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(

            selected = false,
            onClick = {
                onRemove(task)
            }
        )
        Text(text = task.description)
    }
}

@Composable
fun MinimalDropdownMenu(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for(screen in Screen.entries) {
                DropdownMenuItem(
                    onClick = { navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }},
                    content = { Text(screen.title)}
                )
            }

        }
    }
}