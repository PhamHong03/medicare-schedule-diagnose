package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.SpecializationEntity

interface SpecializationRepository {

    suspend fun getSpecializations(): List<SpecializationEntity>
}