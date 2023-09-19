package com.example.eventapp.ui.saved

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventapp.data.SavedEventRepository
import com.example.eventapp.data.local.database.SavedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val eventsRepository: SavedEventRepository,
) : ViewModel() {

    private var job: Job? = null
    var eventsResponse = MutableLiveData<List<SavedEvent>>()

    init {
       job = viewModelScope.launch {
           eventsRepository.savedEvents.collect {events ->
               eventsResponse.postValue(events)
           }
       }
    }

    fun removeEvent(event: SavedEvent) {
        viewModelScope.launch {
            eventsRepository.remove(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}