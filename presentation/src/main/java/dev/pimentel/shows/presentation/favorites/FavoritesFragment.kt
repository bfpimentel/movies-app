package dev.pimentel.shows.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.FavoritesFragmentBinding
import dev.pimentel.shows.presentation.favorites.data.FavoritesIntention
import dev.pimentel.shows.shared.extensions.watch
import dev.pimentel.shows.shared.mvi.handleEvent
import dev.pimentel.shows.shared.shows.ShowsAdapter
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    private lateinit var binding: FavoritesFragmentBinding
    private val viewModel: FavoritesContract.ViewModel by viewModels<FavoritesViewModel>()

    @Inject
    lateinit var adapterFactory: ShowsAdapter.Factory
    private lateinit var adapter: ShowsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavoritesFragmentBinding.bind(view)

        bindRecyclerView()
        bindToolbar()
        bindOutputs()
        bindInputs()
    }

    private fun bindRecyclerView() {
        this.adapter = this.adapterFactory.create(object : ShowsAdapter.ItemListener {
            override fun onItemClick(showId: Int) = viewModel.publish(FavoritesIntention.NavigateToInformation(showId))
            override fun onFavoriteClick(showId: Int) =
                viewModel.publish(FavoritesIntention.FavoriteOrRemoveShow(showId))
        })

        binding.shows.also {
            it.adapter = this.adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindToolbar() {
        binding.toolbar.menu.findItem(R.id.search).actionView.also {
            val searchView = it as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.publish(FavoritesIntention.SearchFavorites(query.orEmpty()))
                    return false
                }
            })
            searchView.setOnCloseListener {
                viewModel.publish(FavoritesIntention.SearchFavorites())
                false
            }
        }
    }

    private fun bindOutputs() {
        watch(viewModel.state) { state ->
            state.showsEvent.handleEvent(adapter::submitList)
        }
    }

    private fun bindInputs() {
        viewModel.publish(FavoritesIntention.SearchFavorites())
    }
}
