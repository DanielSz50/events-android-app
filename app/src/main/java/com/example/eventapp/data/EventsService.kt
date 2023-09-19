package com.example.eventapp.data

import com.example.eventapp.data.ticketmaster.api.EventsApi
import com.example.eventapp.data.ticketmaster.api.EventsRequest
import com.example.eventapp.data.ticketmaster.api.EventsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EventsService {
    val eventsResponse: EventsResponse?

    suspend fun getEvents(request: EventsRequest): EventsResponse?
}

class DefaultEventsService @Inject constructor(
    private val eventsApi: EventsApi
) : EventsService {

    override var eventsResponse: EventsResponse? = null

    override suspend fun getEvents(request: EventsRequest): EventsResponse? {
        val resp = eventsApi.getEvents(
            "<your_api_key>",
            request.size,
            request.segmentNames,
            request.startDate,
            request.latLong,
        )
        withContext(Dispatchers.Main) {
            if (resp.isSuccessful) {
                eventsResponse = resp.body()
            } else {
                println(resp.message())
            }
        }

        return eventsResponse
    }
}