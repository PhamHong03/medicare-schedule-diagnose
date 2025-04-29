package com.example.diagnose_app.domain.usecase.login

data class LoginRequest(
    val email: String,
    val password: String
)