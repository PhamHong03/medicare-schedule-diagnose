package com.example.diagnose_app.presentation.view.doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Examinate(navController: NavController) {
    var examinateText by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "#0293439434343",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues) // Add padding for scaffold content
            ) {
                // Patient info box
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD7EBFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Bệnh nhân", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        PatientInfoRow("Tên", "Phạm Thị Huỳnh Anh")
                        PatientInfoRow("Giới tính", "Nữ")
                        PatientInfoRow("Ngày sinh", "24/12/2012")
                        PatientInfoRow("Số điện thoại", "09131231232")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Diagnosis box (disabled input)
                OutlinedTextField(
                    value = examinateText,
                    onValueChange = {
                        examinateText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    label = { Text("Chẩn đoán sơ bộ của bác sĩ") },
                    placeholder = { Text("Chẩn đoán ...") },
                    enabled = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Process tracking
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD7EBFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Quá trình khám", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(12.dp))

                        ProcessStep(
                            title = "Bác sĩ chẩn đoán",
                            subtitle = "Đang thực hiện...",
                            isDone = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ProcessStep(
                            title = "Chụp XQuang và siêu âm gan",
                            subtitle = "Chuẩn bị thực hiện",
                            isDone = false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        navController.navigate("upload_images")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
                ) {
                    Text(
                        text = "Chuyển sang bước tiếp theo"
                    )
                }
            }
        }
    )
}

@Composable
fun PatientInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text("$label:", modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(2f))
    }
}

@Composable
fun ProcessStep(title: String, subtitle: String, isDone: Boolean) {
    Row(verticalAlignment = Alignment.Top) {
        val icon = if (isDone) Icons.Default.CheckCircle else Icons.Default.Done
        val tint = if (isDone) Color(0xFF4CAF50) else Color(0xFF90A4AE)

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(subtitle, fontSize = 13.sp, color = Color.Gray)
        }
    }
}
