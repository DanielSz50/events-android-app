import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eventapp.R
import com.example.eventapp.data.ticketmaster.api.Event
import com.squareup.picasso.Picasso

class EventsAdapter(
    private val context: Context,
    private var insertEventCallback: ((event: Event) -> Unit),
) : ListAdapter<Event, EventsAdapter.ViewHolder>(DiffUtilItemCallback) {

    object DiffUtilItemCallback: DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)
        val webButton: Button = itemView.findViewById(R.id.webButton)
        val eventName: TextView = itemView.findViewById(R.id.eventName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)

        Picasso.get().load(event.images[0].url.toString()).into(holder.itemImage)
        holder.eventName.text = event.name

        holder.saveButton.setOnClickListener {
            insertEventCallback(event)
        }

        holder.webButton.setOnClickListener {
            val webpage: Uri = Uri.parse(event.url.toString())
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            try {
                startActivity(this.context, intent, null)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this.context, "application not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateAll(newList: List<Event>) {
        submitList(newList)
    }
}