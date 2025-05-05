package com.example.diagnose_app.data.datasource.remote

import com.example.diagnose_app.data.models.CategoryDiseaseEntity
import retrofit2.http.GET

interface CategoryDiseaseApiService {
    @GET("category_disease")
    suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity>
}