package com.example.diagnose_app.infrastructure.repositoryImpl

import android.util.Log
import com.example.diagnose_app.data.datasource.local.RoomDao
import com.example.diagnose_app.data.datasource.remote.RoomApiService
import com.example.diagnose_app.data.models.RoomEntity
import com.example.diagnose_app.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val roomApiService: RoomApiService
): RoomRepository{
    override suspend fun getAllRooms(): List<RoomEntity> {
        return kotlin.runCatching {
            val rooms = roomApiService.getAllRooms()
            roomDao.insertAll(rooms)
            rooms
        }.getOrElse {
            Log.e("RoomRepo", "Lỗi kết nối API: ${it.message}")
            roomDao.getAllRooms()
        }
    }

}