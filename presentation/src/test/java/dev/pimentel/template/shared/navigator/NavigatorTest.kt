package dev.pimentel.template.shared.navigator

import androidx.navigation.NavController
import dev.pimentel.template.R
import dev.pimentel.template.TestDispatchersProvider
import dev.pimentel.template.presentation.example.ExampleFragmentDirections
import dev.pimentel.template.shared.dispatchers.DispatchersProvider
import io.mockk.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NavigatorTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val dispatchersProvider: DispatchersProvider = TestDispatchersProvider(testCoroutineDispatcher)
    private lateinit var navigator: Navigator

    @BeforeEach
    fun `setup subject`() {
        navigator = NavigatorImpl(dispatchersProvider)
    }

    @Test
    fun `should bind navigator and navigate`() = testCoroutineDispatcher.runBlockingTest {
        val navController = mockk<NavController>(relaxed = true)
        val directions = ExampleFragmentDirections.toExampleFragment()

        justRun { navController.navigate(directions) }

        navigator.bind(navController)
        navigator.navigate(directions)

        verify { navController.navigate(directions) }
        confirmVerified(navController)
    }

    @Test
    fun `should unbind navigator and do nothing when trying to navigate`() = testCoroutineDispatcher.runBlockingTest {
        val navController = mockk<NavController>(relaxed = true)
        val directions = ExampleFragmentDirections.toExampleFragment()

        navigator.bind(navController)
        navigator.unbind()
        navigator.navigate(directions)

        confirmVerified(navController)
    }

    @Test
    fun `should bind navigator and pop`() = testCoroutineDispatcher.runBlockingTest {
        val navController = mockk<NavController>(relaxed = true)

        every { navController.popBackStack() } returns true

        navigator.bind(navController)
        navigator.pop()

        verify(exactly = 1) { navController.popBackStack() }
        confirmVerified(navController)
    }

    @Test
    fun `should bind navigator and pop with destination`() = testCoroutineDispatcher.runBlockingTest {
        val navController = mockk<NavController>(relaxed = true)
        val destinationId = R.id.exampleFragment
        val inclusive = true

        every { navController.popBackStack(destinationId, inclusive) } returns true

        navigator.bind(navController)
        navigator.pop(destinationId, inclusive)

        verify(exactly = 1) { navController.popBackStack(destinationId, inclusive) }
        confirmVerified(navController)
    }

    @Test
    fun `should unbind navigator and do nothing when trying to pop`() = testCoroutineDispatcher.runBlockingTest {
        val navController = mockk<NavController>(relaxed = true)

        navigator.bind(navController)
        navigator.unbind()
        navigator.pop()

        confirmVerified(navController)
    }
}
