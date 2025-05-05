package com.example.diagnose_app.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diagnose_app.data.models.PatientEntity

@Dao
interface PatientDao {

    @Query("SELECT * FROM patients")
    suspend fun getAllPatient() : List<PatientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patientEntity: PatientEntity)

    @Query("DELETE FROM patients WHERE id =:id")
    suspend fun deletePatient(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patientEntity: List<PatientEntity>)


    @Query("SELECT * FROM patients WHERE account_id = :accountId LIMIT 1")
    suspend fun getPatientByAccountId(accountId: Int): PatientEntity?

    @Query("SELECT * FROM patients WHERE id = :patientId")
    suspend fun getPatientById(patientId: Int): PatientEntity?

}