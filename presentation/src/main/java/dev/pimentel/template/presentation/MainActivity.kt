package dev.pimentel.template.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dev.pimentel.template.R
import dev.pimentel.template.di.NavigatorBinderQualifier
import dev.pimentel.template.shared.navigator.NavigatorBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    @Inject
    @NavigatorBinderQualifier
    lateinit var navigator: NavigatorBinder

    override fun onResume() {
        super.onResume()

        val navController = supportFragmentManager
            .findFragmentById(R.id.navHostFragment)!!
            .findNavController()

        navigator.bind(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.unbind()
    }
}
