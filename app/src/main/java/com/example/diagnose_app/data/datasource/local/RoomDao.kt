package com.example.diagnose_app.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diagnose_app.data.models.RoomEntity

@Dao
interface RoomDao {

    @Query("SELECT * FROM room")
    suspend fun getAllRooms(): List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(room: List<RoomEntity>)
}