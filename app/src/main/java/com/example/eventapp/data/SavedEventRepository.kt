package com.example.eventapp.data

import com.example.eventapp.data.local.database.SavedEvent
import com.example.eventapp.data.local.database.SavedEventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

interface SavedEventRepository {
    val savedEvents: Flow<List<SavedEvent>>

    suspend fun add(externalID: String, name: String, url: String, imageURL: String)

    suspend fun remove(event: SavedEvent)
}

class DefaultSavedEventRepository @Inject constructor(
    private val savedEventDao: SavedEventDao
) : SavedEventRepository {

    override val savedEvents: Flow<List<SavedEvent>> =
        savedEventDao.getSavedEvents().map { items -> items.map { it } }

    override suspend fun add(externalID: String, name: String, url: String, imageURL: String) {
        savedEventDao.insertSavedEvent(
            SavedEvent(externalID = externalID, name = name, url = url, imageURL = imageURL)
        )
    }

    override suspend fun remove(event: SavedEvent) {
        savedEventDao.deleteSavedEvent(event)
    }
}