package com.example.diagnose_app.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "specializations")
data class SpecializationEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val quantity_patient: Int
)