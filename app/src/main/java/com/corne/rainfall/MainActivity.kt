package com.corne.rainfall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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
        setSupportActionBar(binding.topAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            println(destination.id)
        }

        binding.fab.setOnClickListener {
//            navController.popBackStack()
//            navController.navigate(R.id.navigation_add)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_add);

        }


        with(navController) {
//            appBarConfiguration = AppBarConfiguration(graph)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_settings,
                    R.id.navigation_notifications,
                    R.id.navigation_graph,
                    R.id.navigation_add

                )
            )
            setupActionBarWithNavController(this, appBarConfiguration)
            binding.bottomNavigationView.setupWithNavController(this)

            val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications)
            badge.isVisible = true
            badge.number = 99
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