package com.amanuel.evscsystem

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.amanuel.evscsystem.databinding.ActivityMainBinding

/**
 * Created by Amanuel Girma.
 * Contact Email : amanuelgirma070@gmail.com
 */
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(navController)


        /* START of previous navigation to AuthActivity or HomeActivity*/
//        val userPreferences = UserPreferences(this)
//
//        userPreferences.authToken.asLiveData().observe(this, {
//            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
//            startNewActivity(activity)
//        })
        /* END of previous navigation to AuthActivity or HomeActivity*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}