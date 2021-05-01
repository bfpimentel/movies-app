package dev.pimentel.series.shared.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

inline fun <T> Fragment.watch(source: StateFlow<T>, crossinline block: (T) -> Unit) {
    viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) { source.collect { block(it) } }
}
