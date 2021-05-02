package dev.pimentel.shows

import dev.pimentel.shows.shared.dispatchers.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatchersProvider(testCoroutineDispatcher: TestCoroutineDispatcher) : DispatchersProvider {

    override val ui: CoroutineDispatcher = testCoroutineDispatcher
    override val io: CoroutineDispatcher = testCoroutineDispatcher
}
