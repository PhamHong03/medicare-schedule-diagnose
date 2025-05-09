package com.example.diagnose_app.presentation.view.patients

import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.diagnose_app.presentation.viewmodel.account.SpecializationViewModel
import com.example.diagnose_app.utils.HeaderSection
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarExam(
    selectedDate: Calendar = Calendar.getInstance(),
    onDateSelected: (Calendar) -> Unit = {},
    selectedTime: String? = null,
    onTimeSelected: (String) -> Unit = {},
    onNextClicked: () -> Unit = {},
    selectedSpecializationId: Int?,
    specializationViewModel: SpecializationViewModel,
    navController: NavController
) {
    val availableTimes = listOf("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "13:30 PM", "14:00 PM", "15:30 PM")

    val selectedDay = remember { mutableStateOf(selectedDate) }
    val selectedHour = remember { mutableStateOf(selectedTime) }
    val showPhysicianList = remember { mutableStateOf(false) }
    var selectedPhysicianId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Chọn ngày khám", style = MaterialTheme.typography.titleMedium)

            AndroidView(
                factory = { context ->
                    CalendarView(context).apply {
                        setOnDateChangeListener { _, year, month, day ->
                            val picked = Calendar.getInstance().apply { set(year, month, day) }
                            selectedDay.value = picked
                            onDateSelected(picked)
                        }
                        date = selectedDay.value.timeInMillis
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF6FAFF))
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Chọn giờ", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                availableTimes.forEach { time ->
                    val isSelected = time == selectedHour.value
                    OutlinedButton(
                        onClick = {
                            selectedHour.value = time
                            onTimeSelected(time)
                        },
                        shape = RoundedCornerShape(1.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color(0xFFE0F0FF) else Color.White,
                            contentColor = Color.Black
                        ),
                        border = if (isSelected) BorderStroke(1.dp, Color(0xFF2196F3)) else null
                    ) {
                        Text(time)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (selectedHour.value != null && selectedSpecializationId != null) {
                        showPhysicianList.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9)),
                shape = RoundedCornerShape(8.dp),
                enabled = selectedHour.value != null
            ) {
                Text("Tiếp theo", color = Color.White)
            }
        }
    }

    // ✅ Hiển thị danh sách bác sĩ sau khi đã chọn ngày & giờ
    if (showPhysicianList.value && selectedSpecializationId != null) {
        PhysicianListScreen(
            specializationId = selectedSpecializationId,
            onDismiss = {
                showPhysicianList.value = false
            },
            specializationViewModel = specializationViewModel,
            onSelectedPhysician = { id ->
                selectedPhysicianId = id
                Log.d("Physician", "Selected ID: $id")
                navController.navigate("result-booking")
            }
        )
    }
}
