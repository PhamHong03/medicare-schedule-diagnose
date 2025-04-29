package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.AccountEntity
import com.example.diagnose_app.domain.usecase.login.LoginResponse
import com.example.diagnose_app.domain.usecase.register.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response

interface AccountRepository {
    suspend fun insertAccount(account: AccountEntity)

    suspend fun getAccountById(accountId: Int): AccountEntity?

    suspend fun registerAccount(account: RegisterRequest): Response<ResponseBody>

    suspend fun loginUser(email: String, password: String): LoginResponse?


    suspend fun getAllAccount(): List<AccountEntity>

    suspend fun getPhysicianByAccountId(accountId: Int): Boolean
}