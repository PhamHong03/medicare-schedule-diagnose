package com.example.diagnose_app.presentation.view.doctors

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diagnose_app.R

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.diagnose_app.utils.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingProfile(
    userName: String = "Phạm Thị Cẩm Hồng",
    onLogout: () -> Unit = {}
) {
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Tiếng Việt") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Hồ sơ cá nhân",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp)
        ) {
            // Avatar + Tên người dùng
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Các mục
            SettingItem(icon = R.drawable.ic_user, title = "Thông tin cá nhân")
            SettingItem(icon = R.drawable.bell, title = "Thông báo")

            // Ngôn ngữ
            SettingItem(
                icon = R.drawable.ic_language,
                title = "Ngôn ngữ",
                trailing = {
                    Text(
                        text = selectedLanguage,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { languageExpanded = !languageExpanded }
                    )
                }
            )

            if (languageExpanded) {
                Column(
                    modifier = Modifier.padding(start = 48.dp)
                ) {
                    listOf("Tiếng Việt", "Tiếng Anh", "Tiếng Nhật").forEach { lang ->
                        Text(
                            text = lang,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedLanguage = lang
                                    languageExpanded = false
                                }
                        )
                    }
                }
            }

            SettingItem(icon = R.drawable.ic_darkmode, title = "Chế độ tối")
            SettingItem(icon = R.drawable.ic_help, title = "Trợ giúp")
            SettingItem(icon = R.drawable.ic_team, title = "Hội chẩn")

            // Đăng xuất
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogout() }
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Đăng xuất",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Đăng xuất", color = Color.Red, fontWeight = FontWeight.Bold)
            }

        }
    }
}

@Composable
fun SettingItem(
    icon: Int,
    title: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color(0xFF2D2D2D),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        trailing?.invoke()
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Next",
            tint = Color.Gray
        )
    }
}
