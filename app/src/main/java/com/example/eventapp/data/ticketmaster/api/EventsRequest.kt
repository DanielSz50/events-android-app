package com.example.eventapp.data.ticketmaster.api

data class EventsRequest (
    var size: Int = 10,
    var segmentNames: ArrayList<String> = arrayListOf(),
    var startDate: String = "",
    var latLong: String = "",
)