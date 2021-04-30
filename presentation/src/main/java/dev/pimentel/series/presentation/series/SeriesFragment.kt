package dev.pimentel.series.presentation.series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.series.R
import dev.pimentel.series.databinding.ExampleFragmentBinding
import dev.pimentel.series.presentation.series.data.SeriesIntention
import dev.pimentel.series.shared.extensions.watch

@AndroidEntryPoint
class SeriesFragment : Fragment(R.layout.example_fragment) {

    private lateinit var binding: ExampleFragmentBinding
    private val viewModel: SeriesContract.ViewModel by viewModels<SeriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ExampleFragmentBinding.bind(view)

        bindOutputs()
        bindInputs()
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            binding.example.text = state.example
        }
    }

    private fun bindInputs() {
//        viewModel.publish(SeriesIntention.SearchSeries)
    }
}
