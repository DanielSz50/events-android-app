package com.example.eventapp.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventapp.data.ticketmaster.api.EventsRequest
import com.example.eventapp.data.ticketmaster.api.Segments
import com.example.eventapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment(private val homeViewModel: HomeViewModel) : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val request: EventsRequest = EventsRequest()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            request.latLong = "${location.latitude},${location.longitude}"
        }

        binding.datePicker.setOnDateChangedListener { _, year, month, day ->
            val date = LocalDateTime.of(year, month, day, 0, 0)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            request.startDate = date.format(formatter)
        }

        binding.sportsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                request.segmentNames.add(Segments.Sports.name)
            } else {
                request.segmentNames.remove(Segments.Sports.name)
            }
        }

        binding.musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                request.segmentNames.add(Segments.Music.name)
            } else {
                request.segmentNames.remove(Segments.Music.name)
            }
        }

        binding.moviesSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                request.segmentNames.add(Segments.Film.name)
            } else {
                request.segmentNames.remove(Segments.Film.name)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        homeViewModel.newRequest(request)
    }
}