package com.example.eventapp.data.ticketmaster.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {
    @GET("/discovery/v2/events.json")
    suspend fun getEvents(
        @Query("apikey") apiKey: String,
        @Query("size") size: Int,
        @Query("segmentName") segmentName: List<String>,
        @Query("startDateTime") startDateTime: String,
        @Query("latlong") latLong: String,
    ) : Response<EventsResponse>
}