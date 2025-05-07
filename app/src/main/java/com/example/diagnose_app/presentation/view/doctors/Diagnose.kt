package com.example.diagnose_app.presentation.view.doctors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diagnose_app.R
import com.example.diagnose_app.utils.ConfirmSaveDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Diagnose(
    navController: NavController
) {

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "KẾT QUẢ CHẨN ĐOÁN",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFD6ECFF))
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Patient Info Card
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFD6ECFF))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Thông tin bệnh nhân", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow("Mã đơn đăng ký:", "#9783BAD123123")
                    InfoRow("Bệnh nhân:", "ÔN DĨ PHÀM")
                    InfoRow("Ngày sinh:", "23/12/2004")
                    InfoRow("Giới tính:", "Nữ")
                    InfoRow("Số điện thoại:", "0933636271")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Diagnosis Result
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD6ECFF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text("Tình trạng bệnh:", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Xơ gan giai đoạn 3", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Hình ảnh siêu âm:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.patient), // Replace with your image
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // khoảng cách giữa 2 nút
            ) {
                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Thay đổi")
                }

                Button(
                    onClick = {
                        showDialog = true
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF72C3FF)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Xác nhận", color = Color.White)
                }
            }

        }
    }
    if (showDialog) {
        ConfirmSaveDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
//                selectedApplicationFormId?.let { appFormId ->
//                    patientId?.let { patId ->
//                        appointmentFormViewModel.insertAppointmentForm(
//                            AppointmentFormRequest(description, appFormId)
//                        )
//                        showDialog = false
//                        description = "Tạo phiếu khám"
//                    }
//                }
                showDialog = false
            }
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Bold)
        Text(value)
    }
}
