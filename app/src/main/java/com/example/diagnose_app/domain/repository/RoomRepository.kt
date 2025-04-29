package com.example.diagnose_app.domain.repository

import com.example.diagnose_app.data.models.RoomEntity

interface RoomRepository {

    suspend fun getAllRooms(): List<RoomEntity>
}