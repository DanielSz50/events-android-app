package com.example.eventapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedEventDao(): SavedEventDao
}
