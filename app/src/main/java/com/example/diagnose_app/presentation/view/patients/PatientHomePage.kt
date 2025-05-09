package com.example.diagnose_app.presentation.view.patients

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diagnose_app.R
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PatientViewModel
import com.example.diagnose_app.presentation.viewmodel.account.RoomViewModel
import com.example.diagnose_app.presentation.viewmodel.account.SpecializationViewModel
import com.example.diagnose_app.utils.BottomNavBar
import com.example.diagnose_app.utils.ButtonClick
import com.example.diagnose_app.utils.HeaderSection
import com.example.diagnose_app.utils.NotificationCard
import kotlinx.coroutines.delay

@Composable
fun PatientHomePage(
    navController: NavController,
    patientViewModel: PatientViewModel,
    authViewModel: AuthViewModel,
    roomViewModel: RoomViewModel,
    specializationViewModel: SpecializationViewModel,

){
    // State để điều khiển việc hiển thị BookingScreen
    var isBookingSelected by remember { mutableStateOf(false) }
    val currentUser by authViewModel.account.collectAsState()
    val patientId by patientViewModel.patientId.collectAsState()

    val context = LocalContext.current
    val accountId by authViewModel.account.collectAsState()
    var selectedSpecializationId by remember { mutableStateOf<Int?>(null) }
    var selectedSpecializationName by remember { mutableStateOf<String?>(null) }
    var selectedPhysicianId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(accountId, patientId) {
        accountId?.id?.let { id ->
            patientViewModel.fetchPatientIdByAccountId(id)
        }

        delay(300) // Có thể thay bằng flag ViewModel nếu có

        if (patientId == null) {
            navController.navigate("info-patient") {
                popUpTo("home-patient") { inclusive = true }
            }
        }
    }
//    LaunchedEffect(accountId) {
//        if (accountId != 0) {
//            patientViewModel.fetchPatientIdByAccountId(accountId)
//        }
//    }
//
//    LaunchedEffect(patientId) {
//        if (accountId != 0 && patientId == null) {
//            navController.navigate("info_patient")
//        }
//    }


    LaunchedEffect(Unit) {
        roomViewModel.fetchRoom()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                GridButtons(onBookingClick = { isBookingSelected = true }) // Gọi callback khi nhấn nút Đặt lịch
            }

            item {
                AppointmentSection()
                HistorySection()
            }
        }

        // BottomNavBar luôn hiển thị phía dưới màn hình
        BottomNavBar(navController = navController)
    }

    // Khi isBookingSelected là true, hiển thị BookingScreen với background mờ
    if (isBookingSelected) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Màn hình chính sẽ có background mờ 30%
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x4D000000)) // 30% opacity (0x4D)
            ) {
                // Hiển thị BookingScreen
                BookingScreen(
                    onDismiss = { isBookingSelected = false } ,
                    specializationViewModel = specializationViewModel,
                    onSpecializationSelected = { id ->
                        selectedSpecializationId = id
                        selectedSpecializationName = specializationViewModel.getNameById(id)
                        navController.navigate("calendarExam/$selectedSpecializationId")
                        Log.d("Specialization", "Selected ID: $id")
                    }
                )
            }
        }
    }

//    if (selectedSpecializationId != null && selectedSpecializationName != null) {
//        PhysicianListScreen(
//            specializationId = selectedSpecializationId!!,
//            specializationName = selectedSpecializationName!!,
//            onDismiss = {
//                selectedSpecializationId = null
//                selectedSpecializationName = null
//            },
//            specializationViewModel = specializationViewModel,
//            onSelectedPhysician = {id->
//                selectedPhysicianId = id
//                Log.d("Physician", "Selected ID: $id")
//                navController.navigate("calendarExam")
//            }
//        )
//    }
}

@Composable
fun AppointmentSection() {
    Column(modifier = Modifier.padding(10.dp)) {
        Text("Lịch khám", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF7F7F7)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Doctor Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Ôn Dĩ Phàm - Khoa tim", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("24/12/2025 | 9:00 AM", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), // <- phải có fillMaxWidth
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ButtonClick(
                        onClick = {

                        },
                        text = "Huỷ lịch",
                        modifier = Modifier
                            .weight(1f)
                    )
                    ButtonClick(
                        onClick = {},
                        text = "Đổi lịch khám",
                        modifier = Modifier
                            .weight(1f)
                    )
                }

            }
        }
    }
}

@Composable
fun HistorySection() {
    Column(modifier = Modifier.padding(10.dp)) {
        Text("Lịch sử khám", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        repeat(4) {
            Card(
                shape = RoundedCornerShape(4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF7F7F7)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Doctor Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Ôn Dĩ Phàm - Khoa tim", fontWeight = FontWeight.Bold)
                            Text("24/12/2025 | 9:00 AM", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Chuẩn đoán: bình thường, chả có bệnh gì cả, gan bình thường, tim đập không nhanh :D",
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun GridButtons(onBookingClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeButton(icon = R.drawable.list, label = "Đặt lịch", onClick = onBookingClick)
            HomeButton(icon = R.drawable.camera, label = "BHYT", onClick = {})
            HomeButton(icon = R.drawable.resultt, label = "Tiêm chủng", onClick = {})
            HomeButton(icon = R.drawable.note, label = "Thanh toán", onClick = {})
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
