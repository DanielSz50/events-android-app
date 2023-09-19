package com.example.eventapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventapp.data.ticketmaster.api.EventsRequest
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    var requestResp = MutableLiveData<EventsRequest>()
    private var job: Job? = null

    fun newRequest(request: EventsRequest) {
        job = viewModelScope.launch {
            requestResp.postValue(request)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}