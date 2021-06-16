package com.amanuel.evscsystem

import android.opengl.Visibility
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amanuel.evscsystem.databinding.ActivityMainBinding
import com.amanuel.evscsystem.notification.NotificationHelper
import com.google.android.material.navigation.NavigationView

/**
 * Created by Amanuel Girma.
 * Contact Email : amanuelgirma070@gmail.com
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

//        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.findNavController()
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                // top level destinations (we don't show the navigateUp button)
//                R.id.homeFragment, R.id.searchFragment, R.id.notificationsFragment,
//                // also the login pages are excluded
//                R.id.loginFragment, R.id.forgetPasswordFragment, R.id.emailVerifyFragment
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        // initialize the notification channel
        NotificationHelper.init(this)

        // controller navigation drawer opening and closing in different fragments
        navigatedListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // controller navigation drawer opening and closing in different fragments
    private fun navigatedListener() {
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            when (navDestination.id) {
                navController.graph.startDestination,
                R.id.emailVerifyFragment,
                R.id.forgetPasswordFragment
                -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    val drawerLayout: DrawerLayout = binding.drawerLayout
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            // top level destinations (we don't show the navigateUp button)
//                            R.id.homeFragment,
//                            R.id.searchFragment,
//                            R.id.notificationsFragment,
                            // also the login pages are excluded
                            R.id.loginFragment,
                            R.id.forgetPasswordFragment,
                            R.id.emailVerifyFragment
                        )
                    )

                    binding.appBarMain.toolbar.visibility = View.GONE

                    setupActionBarWithNavController(navController, appBarConfiguration)
                    binding.navView.setupWithNavController(navController)
                }
                else -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    val drawerLayout: DrawerLayout = binding.drawerLayout
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            // top level destinations (we don't show the navigateUp button)
                            R.id.homeFragment,
                            R.id.searchFragment,
                            R.id.notificationsFragment,
                            // also the login pages are excluded
//                            R.id.loginFragment,
//                            R.id.forgetPasswordFragment,
//                            R.id.emailVerifyFragment
                        ), drawerLayout
                    )

                    binding.appBarMain.toolbar.visibility = View.VISIBLE

                    setupActionBarWithNavController(navController, appBarConfiguration)
                    binding.navView.setupWithNavController(navController)
                }
            }
        }
    }


}










