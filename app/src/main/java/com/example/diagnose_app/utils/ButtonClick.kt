package com.example.diagnose_app.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonClick(
    onClick : ()-> Unit,
    text : String,
    modifier: Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color(0xFFCCE4FB)),
        shape = RoundedCornerShape(2.dp)
    ) {
        Text(text = text, color = Color.Black)
    }
}