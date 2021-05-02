package dev.pimentel.shows.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import dev.pimentel.shows.R
import dev.pimentel.shows.databinding.MainActivityBinding
import dev.pimentel.shows.di.NavigatorBinderQualifier
import dev.pimentel.shows.shared.navigator.NavigatorBinder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @NavigatorBinderQualifier
    lateinit var navigator: NavigatorBinder

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val navController = supportFragmentManager
            .findFragmentById(R.id.navHostFragment)!!
            .findNavController()

        NavigationUI.setupWithNavController(binding.navigationView, navController)

        navigator.bind(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.unbind()
    }
}
