package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.PhysicianEntity


interface PhysicianRepository {

    suspend fun insertPhysician(physicianEntity: PhysicianEntity)

    suspend fun getAllPhysician(): List<PhysicianEntity>

    suspend fun deletePhysician(id: Int)

    suspend fun getPhysicianByAccountId(accountId: Int): PhysicianEntity?

    suspend fun getPhysicianIdByAccountId(accountId: Int): Int?

    suspend fun checkPhysicianExists(accountId: Int): Boolean

//    suspend fun getPatientsByPhysician(accountId: Int): List<PatientWithApplicationDate>

}