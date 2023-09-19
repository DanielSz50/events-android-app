package com.example.eventapp.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "saved_events", indices = [Index(value = ["externalID"], unique = true)])
data class SavedEvent(
    val externalID: String,
    val name: String,
    val url: String,
    val imageURL: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface SavedEventDao {
    @Query("SELECT * FROM saved_events ORDER BY id DESC")
    fun getSavedEvents(): Flow<List<SavedEvent>>

    @Insert
    suspend fun insertSavedEvent(item: SavedEvent)

    @Delete
    suspend fun deleteSavedEvent(item: SavedEvent)
}
