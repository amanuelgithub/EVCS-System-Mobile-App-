package com.amanuel.evscsystem.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.ActivityHomeBinding

/**
 * Created by Amanuel Girma.
 * Contact Email : amanuelgirma070@gmail.com
 */
class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeNavHostFragment = supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment
        navController = homeNavHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.notificationsFragment),
            binding.drawerLayout // makes the hambrger icon visible
        )
        // toolbar
        setSupportActionBar(binding.toolbarHome)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // setting up the bottom navigation
        binding.bottomNav.setupWithNavController(navController)
        // nav view
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}