package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.CategoryDiseaseEntity


interface CategoryDiseaseRepository {

    suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity>
}