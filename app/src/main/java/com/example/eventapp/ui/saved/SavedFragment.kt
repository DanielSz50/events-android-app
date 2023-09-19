package com.example.eventapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: SavedEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_saved, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val savedViewModel =
            ViewModelProvider(this)[SavedViewModel::class.java]

        _binding = FragmentSavedBinding.inflate(inflater, container, false)

        eventsAdapter = SavedEventsAdapter(requireActivity()) {
            event -> savedViewModel.removeEvent(event)
        }
        recyclerView.adapter = eventsAdapter

        savedViewModel.eventsResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                eventsAdapter.updateAll(it)
            } else {
                Toast.makeText(this.context, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}