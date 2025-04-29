package com.example.diagnose_app.domain.usecase.register

import com.example.diagnose_app.domain.repository.AccountRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RegisterUsecase @Inject constructor(
    private val authRepository : AccountRepository
){
    suspend fun execute(registerRequest: RegisterRequest): Response<ResponseBody> {
        return authRepository.registerAccount(registerRequest)
    }
}