package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.PatientEntity

interface PatientRepository {

    suspend fun insertPatient(patientEntity: PatientEntity)

    suspend fun getAllPatient(): List<PatientEntity>

    suspend fun deletePatient(id: Int)

    suspend fun getPatientByAccountId(accountId: Int): PatientEntity?

    suspend fun getPatientById(patientId: Int): PatientEntity?
}