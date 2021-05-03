package dev.pimentel.shows.presentation.episode

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.EpisodeFragmentBinding
import dev.pimentel.shows.presentation.episode.data.EpisodeIntention
import dev.pimentel.shows.shared.extensions.watch

@AndroidEntryPoint
class EpisodeFragment : DialogFragment(R.layout.episode_fragment) {

    private lateinit var binding: EpisodeFragmentBinding
    private val viewModel: EpisodeContract.ViewModel by viewModels<EpisodeViewModel>()

    override fun getTheme(): Int = R.style.Theme_Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = EpisodeFragmentBinding.bind(view)

        bindOutputs()
        bindInputs()
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            with(binding) {
                state.imageUrl?.also { imageUrl ->
                    image.isVisible = true
                    image.load(imageUrl)
                } ?: run { image.isVisible = false }

                name.text = state.name
                numbers.text = getString(R.string.episode_numbers, state.season, state.number)
                dates.text = getString(R.string.episode_aired_at, state.airDate, state.airTime)
                summary.text = state.summary.parseAsHtml()
            }
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
