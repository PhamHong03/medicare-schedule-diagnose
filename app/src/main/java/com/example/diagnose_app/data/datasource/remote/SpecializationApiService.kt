package com.example.diagnose_app.data.datasource.remote

import com.example.diagnose_app.data.models.SpecializationEntity
import retrofit2.Response
import retrofit2.http.GET

interface SpecializationApiService {
    @GET("specializations")
    suspend fun getSpecializations(): Response<List<SpecializationEntity>>

}