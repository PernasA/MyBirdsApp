package com.pernasa.mybirdsapp.models.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomBird::class], version = 1)
abstract class RoomBirdsDatabase : RoomDatabase() {
    abstract fun yourDao(): RoomBirdsDao
}
