package dev.pimentel.series.presentation.shows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.series.R
import dev.pimentel.series.databinding.ShowsFragmentBinding
import dev.pimentel.series.presentation.shows.data.ShowsIntention
import dev.pimentel.series.shared.extensions.watch
import dev.pimentel.series.shared.mvi.handleEvent
import javax.inject.Inject

@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.shows_fragment) {

    private lateinit var binding: ShowsFragmentBinding
    private val viewModel: ShowsContract.ViewModel by viewModels<ShowsViewModel>()

    @Inject
    lateinit var adapterFactory: ShowsAdapter.Factory
    private lateinit var adapter: ShowsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ShowsFragmentBinding.bind(view)

        bindAdapter()
        bindOutputs()
        bindInputs()
    }

    private fun bindAdapter() {
        this.adapter = this.adapterFactory.create(object : ShowsContract.ItemListener {})

        binding.shows.also {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            state.showsEvent.handleEvent(adapter::submitList)
        }
    }

    private fun bindInputs() {
        viewModel.publish(ShowsIntention.GetShows)
    }
}
