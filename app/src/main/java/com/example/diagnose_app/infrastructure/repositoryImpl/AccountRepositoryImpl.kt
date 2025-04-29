package com.example.diagnose_app.infrastructure.repositoryImpl

import android.util.Log
import com.example.diagnose_app.data.datasource.local.AccountDAO
import com.example.diagnose_app.data.datasource.remote.ApiService
import com.example.diagnose_app.data.models.AccountEntity
import com.example.diagnose_app.domain.repository.AccountRepository
import com.example.diagnose_app.domain.repository.FirebaseAuthRepository
import com.example.diagnose_app.domain.usecase.TokenRequest
import com.example.diagnose_app.domain.usecase.login.LoginRequest
import com.example.diagnose_app.domain.usecase.login.LoginResponse
import com.example.diagnose_app.domain.usecase.register.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDAO,
    private val apiService: ApiService,
    private val firebaseAuthRepository: FirebaseAuthRepository
): AccountRepository {

    override suspend fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    override suspend fun getAccountById(accountId: Int): AccountEntity? {
        return accountDao.getAccountById(accountId)

    }

    override suspend fun registerAccount(registerRequest: RegisterRequest): Response<ResponseBody> {
        return apiService.registerAccount(registerRequest)
    }
    override suspend fun getAllAccount(): List<AccountEntity> {
        return runCatching {
            val accounts = apiService.getAllAccount()
            accountDao.insertAll(accounts)
            accounts
        }.getOrElse {
            Log.e("AccountRepo", "Lỗi kết nối API: ${it.message}")
            accountDao.getAllAccount()
        }
    }

    override suspend fun getPhysicianByAccountId(accountId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(email: String, password: String): LoginResponse? {
        val response = apiService.login(LoginRequest(email, password))
        return if (response.isSuccessful) {
            response.body()
        } else {
            val error = response.errorBody()?.string()
            Log.e("AccountRepository", "Login failed: $error")
            null
        }
    }

}