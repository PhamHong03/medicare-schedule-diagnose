package com.example.diagnose_app.data.datasource.remote

import com.example.diagnose_app.data.models.AccountEntity
import com.example.diagnose_app.domain.usecase.TokenRequest
import com.example.diagnose_app.domain.usecase.login.LoginRequest
import com.example.diagnose_app.domain.usecase.login.LoginResponse
import com.example.diagnose_app.domain.usecase.register.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun registerAccount(
        @Body account: RegisterRequest
    ): Response<ResponseBody>
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @GET("accounts")
    suspend fun getAllAccount(): List<AccountEntity>

    @POST("get_account_id")
    suspend fun getAccountIdByUid(@Body request: Map<String, String>): Response<LoginResponse>

//    @GET("physicians/account/{accountId}")
//    suspend fun getPhysicianByAccountId(@Path("accountId") accountId: Int): Response<PhysicianEntity?>

}