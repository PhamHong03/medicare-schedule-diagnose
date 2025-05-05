package com.example.diagnose_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "patients")
data class PatientEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val day_of_birth : String,
    val gender : String,
    val phone : String,
    val email: String,
    val job: String,
    val address: String,
    val account_id: Int
)