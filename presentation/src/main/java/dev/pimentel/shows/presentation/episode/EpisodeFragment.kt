package dev.pimentel.shows.presentation.episode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.EpisodeFragmentBinding
import dev.pimentel.shows.presentation.episode.data.EpisodeIntention
import dev.pimentel.shows.shared.extensions.watch

@AndroidEntryPoint
class EpisodeFragment : Fragment(R.layout.episode_fragment) {

    private lateinit var binding: EpisodeFragmentBinding
    private val viewModel: EpisodeContract.ViewModel by viewModels<EpisodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = EpisodeFragmentBinding.bind(view)

        bindOutputs()
        bindInputs()
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->

        }
    }

    private fun bindInputs() {
        val args by navArgs<EpisodeFragmentArgs>()
        viewModel.publish(
            EpisodeIntention.GetEpisode(
                showId = args.showId,
                seasonNumber = args.seasonNumber,
                episodeNumber = args.episodeNumber
            )
        )
    }
}
