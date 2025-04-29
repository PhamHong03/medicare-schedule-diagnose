package com.example.diagnose_app.domain.usecase.register

data class RegisterResponse(
    val id: Int,
    val username:String,
    val email:String,
    val phoneNumber:String,
    val role:String,
    val password: String
)
