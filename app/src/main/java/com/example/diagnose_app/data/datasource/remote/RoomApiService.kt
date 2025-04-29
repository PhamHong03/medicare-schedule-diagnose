package com.example.diagnose_app.data.datasource.remote

import com.example.diagnose_app.data.models.RoomEntity
import retrofit2.http.GET

interface RoomApiService {

    @GET("rooms")
    suspend fun getAllRooms(): List<RoomEntity>
}