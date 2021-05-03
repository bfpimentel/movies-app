package dev.pimentel.shows.shared.extensions

import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import com.google.android.material.appbar.MaterialToolbar

fun MaterialToolbar.addSearchListeners(
    @IdRes menuId: Int,
    onTextChanged: (String?) -> Unit,
    onClose: () -> Unit
) {
    menu.findItem(menuId).actionView.also {
        val searchView = it as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                onTextChanged(query.orEmpty())
                return false
            }
        })

        searchView.setOnCloseListener {
            onClose()
            false
        }
    }
}
