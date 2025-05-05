package com.example.diagnose_app.data.datasource.remote

import com.example.diagnose_app.data.models.DiseaseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DiseaseApiService {

    @GET("diagnose_disease")
    suspend fun getAllDisease(): List<DiseaseEntity>

    @GET("diagnose_disease/{id}")
    suspend fun getDiseaseById(@Path("id") id: Int): Response<DiseaseEntity>

    @POST("diagnose_disease")
    suspend fun insertDisease(@Body diseaseEntity: DiseaseEntity): Response<Void>



}