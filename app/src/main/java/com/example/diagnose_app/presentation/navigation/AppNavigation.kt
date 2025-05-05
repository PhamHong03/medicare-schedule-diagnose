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
import com.example.diagnose_app.presentation.view.doctors.InfoDoctor
import com.example.diagnose_app.presentation.view.patients.InfoPatient
import com.example.diagnose_app.presentation.view.patients.PatientHomePage
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.presentation.viewmodel.account.CategoryDiseaseViewModel
import com.example.diagnose_app.presentation.viewmodel.account.DiseaseViewModel
import com.example.diagnose_app.presentation.viewmodel.account.EducationViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PatientViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PhysicianViewModel
import com.example.diagnose_app.presentation.viewmodel.account.RoomViewModel
import com.example.diagnose_app.presentation.viewmodel.account.SpecializationViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController  = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val specializationViewModel: SpecializationViewModel = viewModel()
    val educationViewModel: EducationViewModel = viewModel()
    val roomViewModel: RoomViewModel = viewModel()
    val physicianViewModel: PhysicianViewModel = viewModel()
    val patientViewModel: PatientViewModel = viewModel()
    val categoryDiseaseViewModel: CategoryDiseaseViewModel = viewModel()
    val diseaseViewModel: DiseaseViewModel = viewModel()
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
            DoctorHomePage(
                authViewModel,
                navController, physicianViewModel,
                categoryDiseaseViewModel = categoryDiseaseViewModel,
                diseaseViewModel = diseaseViewModel,
                roomViewModel = roomViewModel
            )
        }

        composable(route = "info-doctor") {
            InfoDoctor(
                navController = navController,
                specializationViewModel = specializationViewModel,
                authViewModel = authViewModel,
                educationViewModel = educationViewModel,
                physicianViewModel = physicianViewModel
            )
        }

        composable(route = "home-patient") {
            PatientHomePage(
                navController = navController,
                patientViewModel = patientViewModel,
                authViewModel = authViewModel,
                roomViewModel =  roomViewModel
            )
        }

        composable(route = "info-patient") {
            InfoPatient(
                navController = navController,
                patientViewModel = patientViewModel,
                authViewModel = authViewModel
            )
        }

    }
}