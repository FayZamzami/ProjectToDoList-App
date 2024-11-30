package com.example.projectprak

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectprak.model.AuthViewModel
import com.example.projectprak.screen.pages.HomePage
import com.example.projectprak.screen.pages.LoginPage
import com.example.projectprak.screen.pages.ProfilePage
import com.example.projectprak.screen.pages.SignupPage
import com.example.projectprak.screen.pages.SplashPage
import com.example.projectprak.screen.parts.TaskItem


@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash", builder = {
        // MyNavigation.kt
//
        composable("splash") {
            SplashPage(navController = navController, authViewModel)
        }
//
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController, authViewModel)
        }
        composable("finish") {
            ProfilePage(modifier, navController, authViewModel)

        }



    })
}