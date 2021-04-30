package dev.pimentel.template.presentation.example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.template.R
import dev.pimentel.template.databinding.ExampleFragmentBinding
import dev.pimentel.template.presentation.example.data.ExampleIntention
import dev.pimentel.template.shared.extensions.watch

@AndroidEntryPoint
class ExampleFragment : Fragment(R.layout.example_fragment) {

    private lateinit var binding: ExampleFragmentBinding
    private val viewModel: ExampleContract.ViewModel by viewModels<ExampleViewModel>()

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
        viewModel.publish(ExampleIntention.GetExample)
    }
}
