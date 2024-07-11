package com.example.mybirdsapp.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birdsRoomEntity")
data class RoomBird(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var wasObserved: Boolean
)
