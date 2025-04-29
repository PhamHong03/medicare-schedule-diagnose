package com.example.diagnose_app.domain.usecase.login

import com.example.diagnose_app.domain.repository.AccountRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val authRepository : AccountRepository
){
//    suspend operator fun invoke(token:String) : Result<LoginResponse> {
//        return authRepository.loginWithToken(token)
//    }
}