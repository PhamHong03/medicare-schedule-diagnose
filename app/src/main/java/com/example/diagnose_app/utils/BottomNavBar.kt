package com.example.diagnose_app.utils

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.diagnose_app.R

@Composable
fun BottomNavBar(
    navController: NavController
) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier
    ) {
        val items = listOf(
            NavItem(label = "Trang chủ", image = R.drawable.camera, route = ""),
            NavItem(label = "Lịch khám", image = R.drawable.note, route = "appointment-list"),
            NavItem(label = "Lịch sử", image = R.drawable.resultt, route = ""),
            NavItem(label = "Cài đặt", image = R.drawable.bell, route = "setting-profile")
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                          },
                icon = {
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = item.label,
                        modifier = Modifier.height(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        fontWeight = if (selectedItem == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedItem == index) Color(0xFF4C8EFF) else Color.Gray
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

data class NavItem(val label: String, val image: Int, val route: String)