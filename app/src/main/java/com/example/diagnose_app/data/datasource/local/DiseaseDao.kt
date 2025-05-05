package com.example.diagnose_app.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diagnose_app.data.models.DiseaseEntity

@Dao
interface DiseaseDao {

    @Query("SELECT * FROM diagnose_disease WHERE id = :id")
    suspend fun getLocalDiseaseById(id: Int): DiseaseEntity?

    @Query("SELECT * FROM diagnose_disease")
    suspend fun getAllDisease(): List<DiseaseEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(diseases: List<DiseaseEntity>)

}