package com.hexaware.weatherapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.util.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var navController: NavController
    lateinit var navView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navView = findViewById(R.id.nav_view)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//It is show exacte image color which is design send.
        navView.itemIconTintList = null

       //Show devider line
        nav_view.setItemBackgroundResource(R.drawable.navviewline)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, 0
        )

        toggle.toolbarNavigationClickListener =
            View.OnClickListener { drawerLayout.openDrawer(GravityCompat.START) }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.helpFragment, R.id.settingsFragment


            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        nav_view.setupWithNavController(navController)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.addlocationMap -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    //supportActionBar?.setHomeAsUpIndicator(R.drawable.vehicle)
                    toolbar.setNavigationOnClickListener {
                        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                        onBackPressed()
                    }
                    setSupportActionBar(toolbar)
                } R.id.cityWeatherDetail -> {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                //supportActionBar?.setHomeAsUpIndicator(R.drawable.vehicle)
                toolbar.setNavigationOnClickListener {
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    onBackPressed()
                }
                setSupportActionBar(toolbar)
            }

                else -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
            toolbar?.layoutTransition = null
            toolbar?.subtitle = null
        }

        // By Default Save
        Constant.setBooleanSharePreference(
            "CELSIUS",
            true
        )
        Constant.setBooleanSharePreference(
            "FAHRENHEIT",
            false
        )
        Constant.setBooleanSharePreference(
            "LOCATION_RESET",
            false
        )
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

                navController.navigate(R.id.homeFragment)
                supportActionBar?.title = "Home"
            }
            R.id.nav_help -> {
                navController.navigate(R.id.helpFragment)
                supportActionBar?.title = "Help"

            }
            R.id.nav_setting -> {
                navController.navigate(R.id.settingsFragment)

                supportActionBar?.title = "Settings"
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
