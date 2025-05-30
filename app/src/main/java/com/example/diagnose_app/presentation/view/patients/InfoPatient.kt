package com.example.diagnose_app.presentation.view.patients

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diagnose_app.presentation.viewmodel.account.AuthViewModel
import com.example.diagnose_app.presentation.viewmodel.account.EducationViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PatientViewModel
import com.example.diagnose_app.presentation.viewmodel.account.PhysicianViewModel
import com.example.diagnose_app.presentation.viewmodel.account.SpecializationViewModel
import com.example.diagnose_app.utils.ConfirmSaveDialog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun InfoPatient(
    navController: NavController,
    patientViewModel: PatientViewModel,
    authViewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("a@gmail.com") }
    var gender by remember { mutableStateOf("") }
    var day_of_birth by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val account by authViewModel.account.collectAsState()
    val currentUser by authViewModel.account.collectAsState()
    val accountId = currentUser?.id ?: 0
    var isProcessing by remember { mutableStateOf(false) }
    LaunchedEffect (accountId){
        if(accountId != 0){
            patientViewModel.fetchPatientIdByAccountId(accountId)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Thông tin cá nhân", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))

        CustomerInput(label = "Họ và tên", value = name, onValueChange = { name = it })

        Spacer(modifier = Modifier.height(10.dp))

        CustomerInput(
            label = "Giới tính",
            value = gender,
            onValueChange = { gender = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        DatePickerFieldCustomer(
            context = context,
            label = "Chọn ngày sinh",
            date = day_of_birth,
            onDateSelected = { day_of_birth = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomerInput(
            label = "Số điện thoại",
            value = phone,
            onValueChange = { phone = it }
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomerInput(
            label = "Nghề nghiệp",
            value = job,
            onValueChange = { job = it }
        )

        Spacer(modifier = Modifier.height(10.dp))
        CustomerInput(
            label = "Địa chỉ",
            value = address,
            onValueChange = { address = it }
        )


        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Tôi cam kết sẽ chịu trách nhiệm trước pháp luật nếu thông tin trên sai sự thật!",
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Lưu thông tin", color = Color.White, fontSize = 16.sp)
        }
    }

    if (showDialog) {
        ConfirmSaveDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                patientViewModel.insertPatient(
                    account_id = accountId,
                    name = name,
                    day_of_birth = day_of_birth,
                    gender = gender,
                    phone = phone,
                    email = email,
                    job = job,
                    address = address
                )
                showDialog = false
            }
        )
    }

    val isSaved by patientViewModel.isSaved.collectAsState()

    LaunchedEffect(isSaved) {
        if (isSaved) {
            isProcessing = false
            showDialog = false
            Toast.makeText(context, "Đăng ký hồ sơ thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("home-patient") {
                popUpTo("info-patient") { inclusive = true }
            }
            patientViewModel.resetIsSaved()
        }
    }

}


@Composable
fun DoctorDropdownField(
    label: String,
    selectedId: Int,
    onValueChange: (Int) -> Unit,
    options: List<Pair<Int, String>>
){
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E8FF))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = options.find { it.first == selectedId }?.second ?: "Chọn $label",
                    fontSize = 14.sp,
                    color = if (selectedId == 0) Color.Blue else Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onValueChange(id)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
fun DatePickerFieldCustomer(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        },
        year, month, day
    )

    OutlinedTextField(
        value = if (date.isNotEmpty()) formatDateToDisplay(date) else "",
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

fun formatDateToDisplay(date: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(date)
        parsedDate?.let { outputFormat.format(it) } ?: date
    } catch (e: Exception) {
        date
    }
}

@Composable
fun CustomerInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        enabled = true
    )
}

