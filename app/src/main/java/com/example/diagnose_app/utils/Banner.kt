package com.example.diagnose_app.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diagnose_app.R
import com.example.diagnose_app.ui.theme.BannerColor

@Composable
fun Banner(
    padding: Dp,
    text: String
) {
    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        colors = CardDefaults.cardColors(containerColor = BannerColor)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hay cham soc suc khoe cua ban",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.bannerdoctorinput),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 8.dp)
                )
            }
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFF83BDF7))
                ) {
                    Text(
                        text = "See more",
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }
        }
    }
}