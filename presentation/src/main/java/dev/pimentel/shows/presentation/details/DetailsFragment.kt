package dev.pimentel.shows.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.DetailsFragmentBinding

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private lateinit var binding: DetailsFragmentBinding
    private val viewModel: DetailsContract.ViewModel by viewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = DetailsFragmentBinding.bind(view)
    }
}
