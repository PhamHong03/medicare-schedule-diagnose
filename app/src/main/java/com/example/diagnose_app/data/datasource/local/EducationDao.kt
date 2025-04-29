package com.example.diagnose_app.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diagnose_app.data.models.EducationEntity

@Dao
interface EducationDao {

    @Query("SELECT * FROM educations")
    suspend fun getAllEducation(): List<EducationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducation(education: EducationEntity)

    @Query("DELETE FROM educations WHERE id = :id")
    suspend fun deleteEducation(id: Int)

}