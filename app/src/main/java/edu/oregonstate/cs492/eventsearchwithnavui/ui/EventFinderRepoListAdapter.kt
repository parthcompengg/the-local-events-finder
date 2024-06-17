package edu.oregonstate.cs492.eventsearchwithnavui.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.oregonstate.cs492.eventsearchwithnavui.R
import edu.oregonstate.cs492.eventsearchwithnavui.data.EventFinderListJson

class EventFinderRepoListAdapter(
    private val onGitHubRepoClick: (EventFinderListJson) -> Unit
)
    : RecyclerView.Adapter<EventFinderRepoListAdapter.EventFinderRepoViewHolder>() {
    private var gitHubRepoList = listOf<EventFinderListJson>()

    fun updateRepoList(newRepoList: List<EventFinderListJson>?) {
        notifyItemRangeRemoved(0, gitHubRepoList.size)
        gitHubRepoList = newRepoList ?: listOf()
        notifyItemRangeInserted(0, gitHubRepoList.size)
    }

    override fun getItemCount() = gitHubRepoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventFinderRepoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.github_repo_list_item, parent, false)
        return EventFinderRepoViewHolder(itemView, onGitHubRepoClick)
    }

    override fun onBindViewHolder(holder: EventFinderRepoViewHolder, position: Int) {
        holder.bind(gitHubRepoList[position])
    }

    class EventFinderRepoViewHolder(
        itemView: View,
        onClick: (EventFinderListJson) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTV: TextView = itemView.findViewById(R.id.tv_title)
        private val locationTV: TextView = itemView.findViewById(R.id.tv_location)
        private val dateTV: TextView = itemView.findViewById(R.id.tv_date)
        private val eventIconIV: ImageView = itemView.findViewById(R.id.iv_event_icon)
        private val cityStateTV: TextView = itemView.findViewById(R.id.tv_city_state)
        private var currentGitHubRepo: EventFinderListJson? = null

        init {
            itemView.setOnClickListener {
                currentGitHubRepo?.let(onClick)
            }
        }

        fun bind(gitHubRepo: EventFinderListJson) {
            currentGitHubRepo = gitHubRepo
            titleTV.text = gitHubRepo.name
            dateTV.text = gitHubRepo.dates.start.localDate
            locationTV.text = gitHubRepo.embedded.venues[0].name
            //cityStateTV.text = this.getString(R.string.venue_location, gitHubRepo.embedded.venues[0].city.name, gitHubRepo.embedded.venues[0].state.name)
            cityStateTV.text = itemView.context.getString(R.string.venue_location, gitHubRepo.embedded.venues[0].city.name, gitHubRepo.embedded.venues[0].state.name)

            Glide.with(itemView.context)
                .load(gitHubRepo.images[0].url)
                .into(itemView.findViewById(R.id.iv_event_icon))
        }
    }
}