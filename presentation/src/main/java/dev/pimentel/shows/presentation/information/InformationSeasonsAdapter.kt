package dev.pimentel.shows.presentation.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.InformationEpisodeItemBinding
import dev.pimentel.shows.databinding.InformationSeasonItemBinding
import dev.pimentel.shows.presentation.information.data.InformationViewData.SeasonViewData

class InformationSeasonsAdapter @AssistedInject constructor(
    @Assisted private val listener: ItemListener
) : ListAdapter<SeasonViewData, InformationSeasonsAdapter.ViewHolder>(Diff) {

    @AssistedFactory
    interface Factory {
        fun create(listener: ItemListener): InformationSeasonsAdapter
    }

    interface ItemListener {
        fun onSeasonClick(seasonNumber: Int)
        fun onEpisodeClick(episodeId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = InformationSeasonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: InformationSeasonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SeasonViewData) {
            with(binding) {
                arrow.isSelected = item.isOpen
                seasonName.text = root.context.getString(R.string.information_seasons_title, item.number)
                seasonName.setOnClickListener { listener.onSeasonClick(item.number) }
            }

            bindEpisodes(item.episodes)
        }

        private fun bindEpisodes(episodes: List<SeasonViewData.EpisodeViewData>) {
            episodes.map { episode ->
                val episodeBinding = InformationEpisodeItemBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    binding.episodes,
                    false
                )
                with(episodeBinding) {
                    episodeName.text = root.context.getString(
                        R.string.information_episodes_title,
                        episode.number,
                        episode.name
                    )
                    episodeName.setOnClickListener { listener.onEpisodeClick(episode.id) }
                }
                episodeBinding.root
            }.forEach(binding.episodes::addView)
        }
    }


    companion object Diff : DiffUtil.ItemCallback<SeasonViewData>() {
        override fun areItemsTheSame(oldItem: SeasonViewData, newItem: SeasonViewData): Boolean =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: SeasonViewData, newItem: SeasonViewData): Boolean =
            oldItem.isOpen == newItem.isOpen
    }
}
