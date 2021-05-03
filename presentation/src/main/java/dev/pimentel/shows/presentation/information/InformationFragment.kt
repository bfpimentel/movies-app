package dev.pimentel.shows.presentation.information

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.InformationFragmentBinding
import dev.pimentel.shows.presentation.information.data.InformationIntention
import dev.pimentel.shows.shared.extensions.watch

@AndroidEntryPoint
class InformationFragment : Fragment(R.layout.information_fragment) {

    private lateinit var binding: InformationFragmentBinding
    private val viewModel: InformationContract.ViewModel by viewModels<InformationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = InformationFragmentBinding.bind(view)

        bindOutputs()
        bindInputs()
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            binding.title.text = state.name
        }
    }

    private fun bindInputs() {
        val args by navArgs<InformationFragmentArgs>()
        viewModel.publish(InformationIntention.SearchShowInformation(showId = args.showId))
    }
}
