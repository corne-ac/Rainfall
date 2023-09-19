package com.corne.rainfall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.corne.rainfall.data.IRainfallPreferenceManager
import com.corne.rainfall.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var rainfallPreferenceManager: IRainfallPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = binding.navHostFragmentActivityMain.getFragment<NavHostFragment>()
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        with(navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            binding.bottomNavigationView.setupWithNavController(this)
            val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications)
            badge.isVisible = true
            badge.number = 99
        }

        binding.fab.setOnClickListener {
//            navController.popBackStack()
//            navController.navigate(R.id.navigation_add)
            /*   Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
                   .navigate(R.id.navigation_add);*/
//            val action = NavGraphDirections.actionGlobalNavigationAdd()
//            navController.navigate(action)

            navController.popBackStack()
            val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
            navController.navigate(R.id.navigation_add, null, navOptions)

        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            println(destination.id)
        }
        observeDarkModePreference()
        observeOfflineModePreference()
    }

    private fun observeDarkModePreference() {
        rainfallPreferenceManager.uiModeFlow.asLiveData().observe(this) {
            val mode = when (it) {
                true -> AppCompatDelegate.MODE_NIGHT_YES
                false -> AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    public fun observeOfflineModePreference() {
//        AppCompatDelegate.setApplicationLocales()
    }


}