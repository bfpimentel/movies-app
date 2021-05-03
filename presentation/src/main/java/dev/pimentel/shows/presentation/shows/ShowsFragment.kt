package dev.pimentel.shows.presentation.shows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.ShowsFragmentBinding
import dev.pimentel.shows.presentation.shows.data.ShowsIntention
import dev.pimentel.shows.shared.extensions.addEndOfScrollListener
import dev.pimentel.shows.shared.extensions.addSearchListeners
import dev.pimentel.shows.shared.extensions.watch
import dev.pimentel.shows.shared.mvi.handleEvent
import dev.pimentel.shows.shared.shows.ShowsAdapter
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

        bindRecyclerView()
        bindOutputs()
        bindInputs()
    }

    private fun bindRecyclerView() {
        this.adapter = this.adapterFactory.create(object : ShowsAdapter.ItemListener {
            override fun onItemClick(showId: Int) = viewModel.publish(ShowsIntention.NavigateToInformation(showId))
            override fun onFavoriteClick(showId: Int) = viewModel.publish(ShowsIntention.FavoriteOrRemoveShow(showId))
        })

        binding.shows.also {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(context)
            it.addEndOfScrollListener { viewModel.publish(ShowsIntention.GetMoreShows) }
        }
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            state.showsEvent.handleEvent(adapter::submitList)
        }
    }

    private fun bindInputs() {
        binding.toolbar.addSearchListeners(
            menuId = R.id.search,
            onTextChanged = { query -> viewModel.publish(ShowsIntention.SearchShows(query)) },
            onClose = { viewModel.publish(ShowsIntention.GetMoreShows) }
        )

        viewModel.publish(ShowsIntention.GetMoreShows)
    }
}
