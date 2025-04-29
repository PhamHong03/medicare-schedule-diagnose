package com.example.diagnose_app.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: Int,
    val email : String,
    val role : String,
    val password: String = ""
)

