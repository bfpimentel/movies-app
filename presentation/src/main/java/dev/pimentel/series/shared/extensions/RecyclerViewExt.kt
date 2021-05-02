package dev.pimentel.series.shared.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

@Suppress("UNCHECKED_CAST")
fun RecyclerView.addEndOfScrollListener(onLoadMore: () -> Unit) {
    if (this.layoutManager == null) {
        throw IllegalStateException("RecyclerView must have a Layout Manager attached to it")
    }
    addOnScrollListener(EndOfScrollListener(this.layoutManager!!, onLoadMore))
}

private class EndOfScrollListener<T : RecyclerView.LayoutManager>(
    private val layoutManager: T,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = when (layoutManager) {
            is StaggeredGridLayoutManager -> layoutManager.findFirstVisibleItemPositions(null)[0]
            is GridLayoutManager -> layoutManager.findFirstVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
            else -> throw IllegalArgumentException(
                "RecyclerView's LayoutManager must be a StaggeredGridLayoutManager, GridLayoutManager or a " +
                        "LinearLayoutManager"
            )
        }

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) onLoadMore()
    }
}

