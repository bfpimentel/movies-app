package dev.pimentel.shows.presentation.information

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.InformationFragmentBinding
import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.shared.extensions.watch
import dev.pimentel.shows.shared.mvi.handleEvent
import javax.inject.Inject

@AndroidEntryPoint
class InformationFragment : Fragment(R.layout.information_fragment) {

    private lateinit var binding: InformationFragmentBinding
    private val viewModel: InformationContract.ViewModel by viewModels<InformationViewModel>()

    @Inject
    lateinit var adapterFactory: InformationSeasonsAdapter.Factory
    private lateinit var adapter: InformationSeasonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = InformationFragmentBinding.bind(view)

        bindRecyclerView()
        bindOutputs()
        bindInputs()
    }

    private fun bindRecyclerView() {
        this.adapter = this.adapterFactory.create(object : InformationSeasonsAdapter.ItemListener {
            override fun onSeasonClick(seasonNumber: Int) = Unit
            override fun onEpisodeClick(episodeId: Int) = Unit
        })

        binding.seasons.also {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            state.informationEvent.handleEvent { data ->
                with(binding) {
                    poster.load(data.imageUrl)
                    title.text = data.name
                    summary.text = data.summary.parseAsHtml()
                    rating.rating = data.rating
                    favorite.isSelected = data.isFavorite
                }

                adapter.submitList(data.seasons)
            }
        }
    }

    private fun bindInputs() {
        with(binding) {
            favorite.setOnClickListener { viewModel.publish(InformationIntention.FavoriteOrRemoveShow) }
        }

        val args by navArgs<InformationFragmentArgs>()
        viewModel.publish(InformationIntention.SearchShowInformation(showId = args.showId))
    }
}
