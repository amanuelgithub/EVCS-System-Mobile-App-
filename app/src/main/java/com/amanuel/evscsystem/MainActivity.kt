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


    // list of all kinds of toolbar to be displayed in the different pages
    // of the application
    companion object {
        private const val NO_APPBAR_LAYOUT = 1 // no app bar layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        appBarConfiguration = AppBarConfiguration(setOf())

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
//        setupActionBar(appBarConfiguration) // setup action bar with default appBarConfiguration
        setupNavView()
        setupBottomNav()
    }

    /**
     * Change the options menu based on destination (or the type of the fragment)
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            // inflate different types of menus based on destination
            when (navDestination.id) {
                R.id.searchFragment -> {
                    menuInflater.inflate(R.menu.menu_search_fragment, menu)
                }
                else -> super.onCreateOptionsMenu(menu)
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * This function is for reminding that i can do things based on the different destination.
     * It has no other purpose other than this.
     */
    private fun updateUIBasedOnDestination() {
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            when (navDestination.id) {
                navController.graph.startDestination,
                R.id.emailVerifyFragment,
                R.id.forgetPasswordFragment -> {
                    hideBottomNav()
                    hideAppBarLayout()
                }


                else -> {
                    showBottomNav()
                    setAppAppBarLayout()
                }
            }
        }
    }


    private fun hideAppBarLayout() {
        binding.appBarMain.appBarLayout.visibility = View.GONE
    }

    private fun setAppAppBarLayout() {
        binding.appBarMain.appBarLayout.visibility = View.VISIBLE
        setSupportActionBar(binding.appBarMain.toolbar)
        appBarConfiguration = appBarConfigForNavMain()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun appBarConfigForNavMain(): AppBarConfiguration = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            // top level destinations (we don't show the navigateUp button)
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.notificationsFragment
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
        binding.appBarMain.bottomNav.setupWithNavController(navController)
    }

    private fun setupNavView() {
        binding.navView.setupWithNavController(navController)
    }

    private fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun lockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun hideBottomNav() {
        binding.appBarMain.bottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.appBarMain.bottomNav.visibility = View.VISIBLE
    }


}










