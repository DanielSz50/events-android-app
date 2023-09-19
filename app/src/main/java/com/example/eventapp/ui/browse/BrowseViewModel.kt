package com.example.eventapp.ui.browse

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventapp.data.DefaultEventsService
import com.example.eventapp.data.SavedEventRepository
import com.example.eventapp.data.ticketmaster.api.Event
import com.example.eventapp.data.ticketmaster.api.EventsRequest
import com.example.eventapp.data.ticketmaster.api.EventsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val eventsService: DefaultEventsService,
    private val eventsRepository: SavedEventRepository,
): ViewModel() {

    private var tag: String = "favorites-view-model"
    private var job: Job? = null
    var eventsResponse = MutableLiveData<EventsResponse>()


    fun getEvents(req: EventsRequest) {
        job = viewModelScope.launch {
            eventsResponse.postValue(eventsService.getEvents(req))
        }
    }

    suspend fun saveEvent(event: Event) {
        try {
            eventsRepository.add(
                event.id.toString(),
                event.name.toString(),
                event.url.toString(),
                event.images[0].url.toString(),
            )
        } catch (ex: SQLiteConstraintException) {
            Log.d(tag, ex.message.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}