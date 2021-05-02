package dev.pimentel.series.presentation.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.pimentel.series.databinding.ShowsItemBinding
import dev.pimentel.series.presentation.shows.data.ShowViewData

class ShowsAdapter @AssistedInject constructor(
    @Assisted private val listener: ShowsContract.ItemListener
) : ListAdapter<ShowViewData, ShowsAdapter.ViewHolder>(Diff) {

    @AssistedFactory
    interface Factory {
        fun create(listener: ShowsContract.ItemListener): ShowsAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = ShowsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ShowsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShowViewData) {
            with(binding) {
                image.load(item.imageUrl)
                name.text = item.name
                premieredDate.text = item.premieredDate
                status.text = item.status
                rating.rating = item.rating
            }
        }
    }

    private companion object Diff : DiffUtil.ItemCallback<ShowViewData>() {
        override fun areItemsTheSame(oldItem: ShowViewData, newItem: ShowViewData): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ShowViewData, newItem: ShowViewData): Boolean = oldItem == newItem
    }
}
