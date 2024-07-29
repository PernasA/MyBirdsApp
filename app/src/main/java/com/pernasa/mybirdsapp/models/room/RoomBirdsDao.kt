package com.pernasa.mybirdsapp.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomBirdsDao {
    @Insert
    suspend fun insertAll(listRoomBird: List<RoomBird>)

    @Query("SELECT * FROM birdsRoomEntity")
    fun getAllBirds(): Flow<List<RoomBird>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editWasObservedBird(roomBird: RoomBird)

    @Query("SELECT wasObserved FROM birdsRoomEntity WHERE id = :birdId LIMIT 1")
    fun getBirdWasObservedById(birdId: Int): Boolean
}
