package com.amanuel.evscsystem

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amanuel.evscsystem.databinding.ActivityMainBinding
import com.amanuel.evscsystem.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amanuel Girma.
 * Contact Email : amanuelgirma070@gmail.com
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarConfiguration = AppBarConfiguration(setOf())

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.findNavController()

        // initialize the notification channel
        NotificationHelper.init(this)

        initView()

        // controller navigation drawer opening and closing in different fragments
        updateUIBasedOnDestination()
    }

    private fun initView() {
        setupActionBar(appBarConfiguration) // setup action bar with default appBarConfiguration
        setupNavView()
        setupBottomNav()
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
    private fun updateUIBasedOnDestination() {
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            when (navDestination.id) {
                navController.graph.startDestination,
                R.id.emailVerifyFragment,
                R.id.forgetPasswordFragment
                -> {
                    closeDrawer()
                    hideBottomNav()
                    hideActionBar()
                }
                else -> {
                    appBarConfiguration = appBarConfigForNavMain()
                    setupActionBar(appBarConfiguration) // setup action bar with default appBarConfiguration
                    setupNavView()
                    setupBottomNav()

                    openDrawer()
                    showActionBar()
                    showBottomNav()
                }
            }
        }
    }

    private fun appBarConfigForNavMain(): AppBarConfiguration = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            // top level destinations (we don't show the navigateUp button)
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.notificationsFragment,
        ),
        binding.drawerLayout,
        fallbackOnNavigateUpListener = { onSupportNavigateUp() }
    )


    /**
     * This method is not used because in those destinations the ActionBar is hidden
     */
    private fun appBarConfigForNavAuth(): AppBarConfiguration = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            // also the login pages are excluded
            R.id.loginFragment,
            R.id.forgetPasswordFragment,
            R.id.emailVerifyFragment
        ),
        fallbackOnNavigateUpListener = { onSupportNavigateUp() }
    )

    private fun setupBottomNav() {
        binding.appBarMain.contentMain.bottomNav.setupWithNavController(navController)
    }

    private fun setupNavView() {
        binding.navView.setupWithNavController(navController)
    }

    private fun openDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun closeDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun hideBottomNav() {
        binding.appBarMain.contentMain.bottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.appBarMain.contentMain.bottomNav.visibility = View.VISIBLE
    }

    private fun hideActionBar() {
        binding.appBarMain.toolbar.visibility = View.GONE
    }

    private fun showActionBar() {
        binding.appBarMain.toolbar.visibility = View.VISIBLE
    }

    /**
     * action bar is similar with toolbar
     */
    private fun setupActionBar(appBarConfig: AppBarConfiguration) {
        setSupportActionBar(binding.appBarMain.toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
    }

}










