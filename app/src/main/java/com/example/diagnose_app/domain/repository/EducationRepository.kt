package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.EducationEntity

interface EducationRepository {
    suspend fun insertEducation(educationEntity: EducationEntity)


    suspend fun getAllEducations(): List<EducationEntity>

    suspend fun deleteEducation(id: Int)
}