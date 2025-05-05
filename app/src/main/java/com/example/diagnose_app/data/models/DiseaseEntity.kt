package com.example.diagnose_app.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "diagnose_disease",
    foreignKeys = [
        ForeignKey(
            entity = CategoryDiseaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_disease_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DiseaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val diagnose_disease_name: String,
    val diagnose_disease_description: String,
    val category_disease_id: Int
)
