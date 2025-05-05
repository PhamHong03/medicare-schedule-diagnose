package com.example.diagnose_app.data.datasource.remote


import com.example.diagnose_app.data.models.PatientEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PatientApiService {
    @GET("patients")
    suspend fun getAllPatients(): List<PatientEntity>

    @POST("patients")
    suspend fun insertPatient(@Body patientEntity: PatientEntity): Response<Void>
    @PUT("patients/{id}")
    suspend fun updatePatient(@Path("id") id: Int, @Body patientEntity: PatientEntity): Response<Void>

    @DELETE("patients/{id}")
    suspend fun deletePatient(@Path("id") id: Int): Response<Void>

    @GET("patients/account/{account_id}")
    suspend fun getPatientByAccountId(@Path("account_id") accountId: Int): PatientEntity?


}