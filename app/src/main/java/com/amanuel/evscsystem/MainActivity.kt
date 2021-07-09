package com.amanuel.evscsystem

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

        hideAllAppBarLayout()

        initView()

        // controller navigation drawer opening and closing in different fragments
        updateUIBasedOnDestination()
    }

    // hide all appbar layouts
    private fun hideAllAppBarLayout() {
        binding.apply {
            appBarMain.apply {
                homeFragAppBarLayout.visibility = View.GONE
                searchFragAppBarLayout.visibility = View.GONE
            }
        }
    }


    private fun initView() {
//        setupActionBar(appBarConfiguration) // setup action bar with default appBarConfiguration
        setupNavView()
        setupBottomNav()
    }

    /** need a modification */
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

    // controller navigation drawer opening and closing in different fragments
    private fun updateUIBasedOnDestination() {
        navController.addOnDestinationChangedListener { navController, navDestination, arguments ->
            when (navDestination.id) {
                navController.graph.startDestination,
                R.id.emailVerifyFragment,
                R.id.forgetPasswordFragment
                -> {
                    lockDrawer()
                    hideBottomNav()

                    hideActionBarForHomeFragment()
                    hideActionBarForSearchFragment()
                }

//                R.id.searchFragment -> {
//
//                    // hide the search appbar layout
//                    hideActionBarForHomeFragment()
//                    setupActionBarForSearchFragment()
//
//                    setupNavView()
//                    setupBottomNav()
//
//                    unlockDrawer()
//                    showBottomNav()
//                }


                R.id.homeFragment -> {
                    hideActionBarForSearchFragment()
                    setupActionBarForHomeFragment()

                    setupNavView()
                    setupBottomNav()

                    unlockDrawer()
                    showBottomNav()
                }
                else -> {
                    // hides all actionbars
                    hideActionBarForSearchFragment()
                    hideActionBarForHomeFragment()

                    appBarConfiguration = appBarConfigForNavMain()
//                    setupActionBar(appBarConfiguration) // setup action bar with default appBarConfiguration
                    setupNavView()
                    setupBottomNav()

                    unlockDrawer()
//                    showActionBar() // modified
                    showBottomNav()
                }
            }
        }
    }


    private fun hideActionBarForSearchFragment() {
        binding.appBarMain.searchFragAppBarLayout.visibility = View.GONE
    }

    private fun setupActionBarForSearchFragment() {
        binding.appBarMain.searchFragAppBarLayout.visibility = View.VISIBLE
        setSupportActionBar(binding.appBarMain.toolbarSearchFrag)
        appBarConfiguration = appBarConfigForNavMain()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    private fun hideActionBarForHomeFragment() {
        binding.appBarMain.homeFragAppBarLayout.visibility = View.GONE
    }

    private fun setupActionBarForHomeFragment() {
        binding.appBarMain.homeFragAppBarLayout.visibility = View.VISIBLE
        setSupportActionBar(binding.appBarMain.toolbarHomeFrag)
        appBarConfiguration = appBarConfigForNavMain()
        setupActionBarWithNavController(navController, appBarConfiguration)
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










