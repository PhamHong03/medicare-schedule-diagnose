package com.example.diagnose_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.diagnose_app.presentation.view.Login
import com.example.diagnose_app.presentation.view.Register
import com.example.diagnose_app.presentation.view.doctors.DoctorHomePage
import com.example.diagnose_app.presentation.view.patients.PatientHomePage
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController  = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") {
            Login(navController, authViewModel)
        }

        composable(route = "sign-up") {
            Register(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable(route = "home-doctor") {
            DoctorHomePage()
        }

        composable(route = "home-patient") {
            PatientHomePage()
        }

    }
}