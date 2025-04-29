package com.example.diagnose_app.domain.usecase.register

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String
)
