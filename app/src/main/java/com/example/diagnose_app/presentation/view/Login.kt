package com.example.diagnose_app.presentation.view

import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diagnose_app.R
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.ui.theme.ButtonColor
import com.example.diagnose_app.utils.Social
import kotlinx.coroutines.flow.collectLatest


@Composable
fun Login(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val loginState by viewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.fetchAccount()
    }

    LaunchedEffect(loginState) {
        loginState?.let { result ->
            result.onSuccess {
                viewModel.userRole.collectLatest { updatedRole ->
                    if (!updatedRole.isNullOrEmpty() && updatedRole != "Unknown") {
                        println("🔍 ROLE NHẬN ĐƯỢC: $updatedRole")

                        val destination = when (updatedRole) {
                            "doctor" -> "home-doctor"
                            "patient" -> "home-patient"
                            else -> {
                                Toast.makeText(context, "Lỗi: Role không hợp lệ ($updatedRole)", Toast.LENGTH_SHORT).show()
                                "sign-up"
                            }
                        }

                        navController.navigate(destination) {
                            Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        println("⚠️ Role chưa cập nhật, đợi thêm...")
                    }
                }
            }.onFailure { error ->
                Toast.makeText(context, error.message ?: "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetLoginState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "HEALTH & DIAGNOSE",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("example@example.com") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {

            },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("********") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(mask = '*'),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(email, password)
                }else{
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Đăng nhập",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)   
        Spacer(modifier = Modifier.height(24.dp))

        Social()  
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(text = "Bạn chưa có tài khoản? ")
            Text(
                text = "Đăng ký",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("sign-up")
                }
            )
        }
    }
}
