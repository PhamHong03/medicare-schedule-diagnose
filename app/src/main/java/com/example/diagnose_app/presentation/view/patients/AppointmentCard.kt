package com.example.diagnose_app.presentation.view.patients

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.diagnose_app.R
import com.example.diagnose_app.utils.HeaderSection


@Composable
fun AppointmentCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("Ngày khám:", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(10.dp))

            Text("11/02/2025", modifier = Modifier.padding(bottom = 8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Giờ khám:", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(10.dp))
            Text("09:30 AM", modifier = Modifier.padding(bottom = 8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Bác sĩ:", fontWeight = FontWeight.Medium)

        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD7EBFF)),

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Doctor",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Dr. Lê Dương Bảo Lâm", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Thạc sĩ | Khoa ngoại lồng ngực", maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Phòng khám:", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(10.dp))

            Text("205 (tầng 2)")
        }

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống cuối màn hình

        Button(
            onClick = { /* TODO: Đặt lịch */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Đặt lịch", color = Color.White)
        }
    }
}

