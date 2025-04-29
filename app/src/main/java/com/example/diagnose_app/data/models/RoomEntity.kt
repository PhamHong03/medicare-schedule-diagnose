package com.example.diagnose_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity (
    @PrimaryKey val id: String = "",
    val name: String
)