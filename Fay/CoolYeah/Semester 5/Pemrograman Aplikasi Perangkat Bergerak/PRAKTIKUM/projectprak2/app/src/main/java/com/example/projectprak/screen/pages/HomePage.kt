package com.example.projectprak.screen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectprak.R
import com.example.projectprak.model.AuthViewModel
import com.example.projectprak.model.TaskViewModel
import com.example.projectprak.screen.parts.TaskItem
import com.example.projectprak.screen.parts.ProfileSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    taskViewModel: TaskViewModel = viewModel()
) {
    val authState = authViewModel.authState.observeAsState()
    val username by authViewModel.username.observeAsState("User")
    val nim by authViewModel.nim.observeAsState("00000000000")
    var newTaskTitle by remember { mutableStateOf("") }
    val tasks by taskViewModel.tasks.observeAsState(emptyList())
    val vintageFont = FontFamily(
        Font(R.font.arvo_bold, FontWeight.Normal)
    )
    var searchQuery by remember { mutableStateOf("") }
    var showCompletedTasks by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        taskViewModel.fetchTasks()
    }

    LaunchedEffect(authState.value) {
        if (authState.value is com.example.projectprak.model.AuthState.Unauthenticated) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFD1C4E9), Color(0xFF9575CD))
                )
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "To-Do Work",
                    fontSize = 40.sp,
                    fontFamily = vintageFont,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Profile Section
            ProfileSection(
                username = username,
                nim = nim,
                vintageFont = vintageFont,
                navController = navController
            )

            // Greeting
            Text(
                text = "Hello, $username!",
                fontSize = 24.sp,
                fontFamily = vintageFont,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.White
            )

            // Task Input Section
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("New Task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = Color.White,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.White,
                        backgroundColor = Color.Blue.copy(alpha = 0.4f)
                    ),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(16.dp)
            )

            // Row for Add Task, Filter Completed, and Complete All Buttons
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Filter Completed Button
                Button(
                    onClick = { showCompletedTasks = !showCompletedTasks },
                    modifier = Modifier.wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showCompletedTasks) Color(0xFFFF5722) else Color(0xFFFF5722)
                    )
                ) {
                    Text(if (showCompletedTasks) "Show All Tasks" else "Show Completed Tasks")
                }

                // Add Task Button
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addTask(newTaskTitle)
                            newTaskTitle = "" // Clear the input after adding a task
                        }
                    },
                    modifier = Modifier.wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF5722))
                ) {
                    Text("Add Task")
                }

            }

            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Tasks") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = Color.White,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.White,
                        backgroundColor = Color.Blue.copy(alpha = 0.4f)
                    ),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(16.dp)
            )

            // Tasks List Section
            Text(
                text = if (showCompletedTasks) "Completed Tasks" else "Task List",
                fontSize = 24.sp,
                fontFamily = vintageFont,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp),
                color = Color.White
            )

            val filteredTasks = tasks.filter {
                it.title.contains(searchQuery, ignoreCase = true) &&
                        (if (showCompletedTasks) it.completed else true)
            }

            Box(
                modifier = Modifier
                    .height(310.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(filteredTasks) { task ->
                        TaskItem(
                            task = task,
                            onDelete = { taskViewModel.deleteTask(task.id) },
                            onCompletionChange = { isCompleted ->
                                taskViewModel.updateTaskCompletion(task.id, isCompleted)
                            }
                        )
                    }
                }
            }


// SignOut button
            Button(
                onClick = {
                    authViewModel.signout()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Sign Out")
            }
        }
    }
}