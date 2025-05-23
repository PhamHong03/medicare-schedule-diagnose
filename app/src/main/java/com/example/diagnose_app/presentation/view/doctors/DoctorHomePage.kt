package com.example.diagnose_app.presentation.view.doctors

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diagnose_app.R
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.presentation.viewmodel.account.CategoryDiseaseViewModel
import com.example.diagnose_app.presentation.viewmodel.account.DiseaseViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PhysicianViewModel
import com.example.diagnose_app.presentation.viewmodel.account.RoomViewModel
import com.example.diagnose_app.utils.BottomNavBar
import com.example.diagnose_app.utils.ButtonClick
import com.example.diagnose_app.utils.ConfirmSaveDialog
import com.example.diagnose_app.utils.HeaderSection
import com.example.diagnose_app.utils.NotificationCard
import kotlinx.coroutines.delay

@Composable
fun DoctorHomePage(
    authViewModel: AuthViewModel,
    navController: NavController,
    physicianViewModel: PhysicianViewModel,
    categoryDiseaseViewModel: CategoryDiseaseViewModel,
    diseaseViewModel: DiseaseViewModel,
    roomViewModel: RoomViewModel
) {
    val accountId by authViewModel.account.collectAsState()
    val physicianId by physicianViewModel.physicianId.collectAsState()

    LaunchedEffect(accountId, physicianId) {
        accountId?.id?.let { id ->
            physicianViewModel.fetchPhysicianByAccountId(id)
        }

        delay(300) // Có thể thay bằng flag ViewModel nếu có

        if (physicianId == null) {
            navController.navigate("info-doctor") {
                popUpTo("home-doctor") { inclusive = true }
            }
        }
    }
 LaunchedEffect(Unit) {
     categoryDiseaseViewModel.fetchCategoryDisease()
     diseaseViewModel.fetchDisease()
     roomViewModel.fetchRoom()
 }
    if (physicianId != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    HeaderSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    NotificationCard()
                    Spacer(modifier = Modifier.height(16.dp))
                    GridButtons(navController)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Lịch khám trong ngày",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(3) {
                    AppointmentItem(name = "Ổn Dĩ Phạm", age = "20", time = "9:00 AM - 9:30 AM")
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Lịch trực ca đêm:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        ShiftChip(day = "T2")
                        ShiftChip(day = "T4")
                        ShiftChip(day = "T5")
                        ShiftChip(day = "T7")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            BottomNavBar(navController = navController)
        }
    }
}



@Composable
fun GridButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeButton(icon = R.drawable.list, label = "Lịch khám") {
                navController.navigate("appointment-list")
            }
            HomeButton(icon = R.drawable.camera, label = "Chẩn đoán") {
                navController.navigate("diagnosis")
            }
            HomeButton(icon = R.drawable.resultt, label = "Tiêm chủng") {
                navController.navigate("vaccination")
            }
            HomeButton(icon = R.drawable.note, label = "Chuyên viên") {
                navController.navigate("specialist")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeButton(icon = R.drawable.list, label = "Danh sách") {
                navController.navigate("patient-list")
            }
            HomeButton(icon = R.drawable.note, label = "Tra cứu y tế") {
                navController.navigate("medical-lookup")
            }
            HomeButton(icon = R.drawable.setting, label = "Cài đặt") {
                navController.navigate("settings")
            }
            Spacer(modifier = Modifier.width(72.dp)) // dư 1 chỗ
        }
    }
}
@Composable
fun HomeButton(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() }
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F0FA)),
            modifier = Modifier.size(64.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = Color(0xFF2979FF),
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(72.dp)
        )
    }
}

@Composable
fun AppointmentItem(name: String, age: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Patient",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = "$age tuổi")
                Text(text = time, fontSize = 12.sp, color = Color.Gray)
            }
            ButtonClick(onClick = {}, text = "Khám bệnh", modifier = Modifier)
        }
    }
}

@Composable
fun ShiftChip(day: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFCCE4FB),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = day,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 14.sp
        )
    }
}
