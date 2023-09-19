package com.example.eventapp.ui.saved

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.data.local.database.SavedEvent
import com.squareup.picasso.Picasso
import java.lang.IndexOutOfBoundsException

class SavedEventsAdapter(
    private val context: Context,
    private var deleteEventCallback: ((event: SavedEvent) -> Unit),
) : ListAdapter<SavedEvent, SavedEventsAdapter.ViewHolder>(DiffUtilItemCallback) {

    private val tag = "SavedEventsAdapter"

    object DiffUtilItemCallback: DiffUtil.ItemCallback<SavedEvent>() {
        override fun areItemsTheSame(oldItem: SavedEvent, newItem: SavedEvent): Boolean {
            return oldItem.externalID == newItem.externalID
        }

        override fun areContentsTheSame(oldItem: SavedEvent, newItem: SavedEvent): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val deleteButton: Button = itemView.findViewById(R.id.saveButton)
        val webButton: Button = itemView.findViewById(R.id.webButton)
        val eventName: TextView = itemView.findViewById(R.id.eventName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)

        Picasso.get().load(event.imageURL).into(holder.itemImage)
        holder.eventName.text = event.name

        holder.deleteButton.setOnClickListener {
            val newEvents = ArrayList(currentList)
            try {
                newEvents.removeAt(position)
            } catch (ex: IndexOutOfBoundsException) {
                Log.d(tag, "could not remove item, index out of bound")
            }
            submitList(newEvents)
            deleteEventCallback(event)
        }

        holder.webButton.setOnClickListener {
            val webpage: Uri = Uri.parse(event.url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            try {
                ContextCompat.startActivity(this.context, intent, null)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this.context, "application not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateAll(newList: List<SavedEvent>) {
        submitList(newList)
    }
}