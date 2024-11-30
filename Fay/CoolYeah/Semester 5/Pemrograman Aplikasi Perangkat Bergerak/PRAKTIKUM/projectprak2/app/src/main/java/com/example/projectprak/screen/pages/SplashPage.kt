package com.example.projectprak.screen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectprak.model.AuthState
import com.example.projectprak.model.AuthViewModel
import com.example.projectprak.screen.parts.Logo
import com.example.projectprak.screen.parts.Logo_txt
import kotlinx.coroutines.delay

@Composable
fun SplashPage(navController: NavController, authViewModel: AuthViewModel) {
    // Observe the authentication state
    val authState by authViewModel.authState.observeAsState()

    // Delay and navigation logic
    LaunchedEffect(authState) {
        // Wait for 5 seconds
        delay(5000)

        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            else -> { /* Do nothing if loading or error */ }
        }
    }

    // Splash screen layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFD1C4E9), // Ungu pastel
                        Color(0xFF9575CD)  // Ungu gelap
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo Image
            Logo_txt()

            // Spacer to add space between logo and text
            Spacer(modifier = Modifier.height(16.dp))

            // Circular loading indicator
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}
