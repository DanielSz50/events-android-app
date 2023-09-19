package com.example.eventapp.ui.browse

import EventsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.databinding.FragmentBrowseBinding
import com.example.eventapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseFragment(private val homeViewModel: HomeViewModel) : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val browseViewModel =
            ViewModelProvider(this)[BrowseViewModel::class.java]

        _binding = FragmentBrowseBinding.inflate(inflater, container, false)

        eventsAdapter = EventsAdapter(requireActivity()) {
            event -> browseViewModel.viewModelScope.launch {
                browseViewModel.saveEvent(event)
            }
        }
        recyclerView.adapter = eventsAdapter

        browseViewModel.eventsResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                eventsAdapter.updateAll(it.eventsData!!.events)
            } else {
                Toast.makeText(this.context, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.requestResp.observe(viewLifecycleOwner) {
            if (it != null) {
                browseViewModel.getEvents(it)
            }
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}