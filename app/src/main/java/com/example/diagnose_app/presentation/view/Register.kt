package com.example.diagnose_app.presentation.view

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.diagnose_app.R
import com.example.diagnose_app.domain.usecase.register.RegisterRequest
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.ui.theme.ButtonColor
import com.example.diagnose_app.ui.theme.checkBoxSelected
import com.example.diagnose_app.utils.Social
import kotlinx.coroutines.delay

@Composable
fun Register(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(authState) {
        println("Auth State: $authState")
        authState?.let { result ->
            when {
                result.isSuccess -> {
                    println("Đăng ký thành công: ${result.getOrNull()}")
                    Toast.makeText(context, result.getOrNull(), Toast.LENGTH_SHORT).show()
                    delay(1000)
                    navController.navigate("login"){
                        popUpTo("sign-up") { inclusive = true }
                    }
                }
                result.isFailure -> {
                    println("Đăng ký thất bại: ${result.exceptionOrNull()?.message}")
                    Toast.makeText(context, result.exceptionOrNull()?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
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
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(30.dp))
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
            trailingIcon = {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Done else Icons.Filled.Lock,
                    contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                    modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                )
            },
            visualTransformation = PasswordVisualTransformation(mask = '*'),
//            isPasswordField = true,
//            isPasswordVisible = isPasswordVisible,
            shape = RoundedCornerShape(12.dp)
        )
        RoleSelection(role = role, onRoleSelected = { selectedRole ->
            role = selectedRole
        })
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val accountRequest = RegisterRequest(
                    email = email,
                    password = password,
                    role = role
                )
                viewModel.registerAccount(accountRequest)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Đăng ký",
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
            Text(text = "Bạn đã có tài khoản? ")
            Text(
                text = "Đăng nhập",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {

                }
            )
        }
    }
}

@Composable
fun RoleSelection(role: String, onRoleSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = role == "doctor",
                    onCheckedChange = {
                        if (it) onRoleSelected("doctor")
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = checkBoxSelected,
                        uncheckedColor = Color.Gray
                    )
                )
                Text(text = "Bác sĩ")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = role == "patient",
                    onCheckedChange = {
                        if (it) onRoleSelected("patient")
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = checkBoxSelected,
                        uncheckedColor = Color.Gray
                    )
                )
                Text(text = "Người dùng")
            }
        }
    }
}
