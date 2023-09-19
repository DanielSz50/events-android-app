package com.example.eventapp.data.di

import com.example.eventapp.data.DefaultEventsService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.example.eventapp.data.DefaultSavedEventRepository
import com.example.eventapp.data.EventsService
import com.example.eventapp.data.SavedEventRepository
import com.example.eventapp.data.local.database.SavedEvent
import com.example.eventapp.data.ticketmaster.api.EventsRequest
import com.example.eventapp.data.ticketmaster.api.EventsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsSavedEventRepository(
        savedEventRepository: DefaultSavedEventRepository
    ): SavedEventRepository

    @Singleton
    @Binds
    fun bindsEventsService(
        eventsService: DefaultEventsService
    ): EventsService
}

class FakeSavedEventRepository @Inject constructor() : SavedEventRepository {
    override val savedEvents: Flow<List<SavedEvent>> = flowOf(fakeSavedEvents)

    override suspend fun add(externalID: String, name: String, url: String, imageURL: String) {
        throw NotImplementedError()
    }

    override suspend fun remove(event: SavedEvent) {
        throw NotImplementedError()
    }
}

class FakeEventsService @Inject constructor() : EventsService {
    override val eventsResponse: EventsResponse? = null

    override suspend fun getEvents(request: EventsRequest): EventsResponse? {
        throw NotImplementedError()
    }
}

val fakeSavedEvents = listOf(SavedEvent("1", "1", "1", "1"), SavedEvent("1","2", "2", "2"))
