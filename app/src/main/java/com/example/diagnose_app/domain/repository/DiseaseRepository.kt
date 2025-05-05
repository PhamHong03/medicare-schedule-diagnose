package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.DiseaseEntity
import okhttp3.Response

interface DiseaseRepository {

    suspend fun getAllDisease(): List<DiseaseEntity>

    suspend fun getDiseaseById(id: Int): DiseaseEntity?
}