package com.example.eventapp.data.ticketmaster.api

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class EventsResponse (

    @SerializedName("_embedded" ) var eventsData : EventsData? = EventsData(),
    @SerializedName("page"      ) var page     : Page?     = Page()

)

data class EventsData (

    @SerializedName("events" ) var events : ArrayList<Event> = arrayListOf()

)

data class Event (

    @SerializedName("name"            ) var name            : String?                       = null,
    @SerializedName("type"            ) var type            : String?                       = null,
    @SerializedName("id"              ) var id              : String?                       = null,
    @SerializedName("test"            ) var test            : Boolean?                      = null,
    @SerializedName("url"             ) var url             : String?                       = null,
    @SerializedName("locale"          ) var locale          : String?                       = null,
    @SerializedName("images"          ) var images          : ArrayList<Image>              = arrayListOf(),
    @SerializedName("sales"           ) var sales           : Sales?                        = Sales(),
    @SerializedName("dates"           ) var dates           : Dates?                        = Dates(),
    @SerializedName("classifications" ) var classifications : ArrayList<Classifications>    = arrayListOf(),
    @SerializedName("promoter"        ) var promoter        : Promoter?                     = Promoter(),
    @SerializedName("_embedded"       ) var venuesData        : VenuesData?                 = VenuesData()

)

data class Image (

    @SerializedName("ratio"    ) var ratio    : String?  = null,
    @SerializedName("url"      ) var url      : String?  = null,
    @SerializedName("width"    ) var width    : Int?     = null,
    @SerializedName("height"   ) var height   : Int?     = null,
    @SerializedName("fallback" ) var fallback : Boolean? = null

)

data class Public (

    @SerializedName("startDateTime" ) var startDateTime : String?  = null,
    @SerializedName("startTBD"      ) var startTBD      : Boolean? = null,
    @SerializedName("endDateTime"   ) var endDateTime   : String?  = null

)

data class Sales (

    @SerializedName("public" ) var public : Public? = Public()

)

data class Start (

    @SerializedName("localDate"      ) var localDate      : String?  = null,
    @SerializedName("dateTBD"        ) var dateTBD        : Boolean? = null,
    @SerializedName("dateTBA"        ) var dateTBA        : Boolean? = null,
    @SerializedName("timeTBA"        ) var timeTBA        : Boolean? = null,
    @SerializedName("noSpecificTime" ) var noSpecificTime : Boolean? = null

)

data class Status (

    @SerializedName("code" ) var code : String? = null

)

data class Dates (

    @SerializedName("start"    ) var start    : Start?  = Start(),
    @SerializedName("timezone" ) var timezone : String? = null,
    @SerializedName("status"   ) var status   : Status? = Status()

)

data class Segment (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Genre (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class SubGenre (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Classifications (

    @SerializedName("primary"  ) var primary  : Boolean?  = null,
    @SerializedName("segment"  ) var segment  : Segment?  = Segment(),
    @SerializedName("genre"    ) var genre    : Genre?    = Genre(),
    @SerializedName("subGenre" ) var subGenre : SubGenre? = SubGenre()

)

data class Promoter (

    @SerializedName("id" ) var id : String? = null

)

data class VenuesData (
    @SerializedName("venues"      ) var venues      : ArrayList<Venues>      = arrayListOf()

)

data class City (

    @SerializedName("name" ) var name : String? = null

)

data class State (

    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("stateCode" ) var stateCode : String? = null

)

data class Country (

    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("countryCode" ) var countryCode : String? = null

)

data class Address (

    @SerializedName("line1" ) var line1 : String? = null

)

data class Location (

    @SerializedName("longitude" ) var longitude : String? = null,
    @SerializedName("latitude"  ) var latitude  : String? = null

)

data class Markets (

    @SerializedName("id" ) var id : String? = null

)

data class Venues (

    @SerializedName("name"       ) var name       : String?            = null,
    @SerializedName("type"       ) var type       : String?            = null,
    @SerializedName("id"         ) var id         : String?            = null,
    @SerializedName("test"       ) var test       : Boolean?           = null,
    @SerializedName("locale"     ) var locale     : String?            = null,
    @SerializedName("postalCode" ) var postalCode : String?            = null,
    @SerializedName("timezone"   ) var timezone   : String?            = null,
    @SerializedName("city"       ) var city       : City?              = City(),
    @SerializedName("state"      ) var state      : State?             = State(),
    @SerializedName("country"    ) var country    : Country?           = Country(),
    @SerializedName("address"    ) var address    : Address?           = Address(),
    @SerializedName("location"   ) var location   : Location?          = Location(),
    @SerializedName("markets"    ) var markets    : ArrayList<Markets> = arrayListOf(),

)

data class Page (

    @SerializedName("size"          ) var size          : Int? = null,
    @SerializedName("totalElements" ) var totalElements : Int? = null,
    @SerializedName("totalPages"    ) var totalPages    : Int? = null,
    @SerializedName("number"        ) var number        : Int? = null

)