package com.example.diagnose_app.domain.usecase.login

//data class LoginResponse(
//    val id: Int,
//    val email: String,
//    val password: String,
//    val role: String
//)
//


data class LoginResponse(
    val message: String,
    val account: AccountData
)

data class AccountData(
    val id: Int,
    val email: String,
    val role: String,
    val password: String? = null // nếu không có thì để optional
)