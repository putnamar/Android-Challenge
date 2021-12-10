package com.podium.technicalchallenge

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationBarView
import com.podium.technicalchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    // TODO: Make top count dependent on screen size
    // TODO: Adjust gridlayoutmanagers for screen size
    // TODO: Make by Genre, By Category

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(binding.root)

        binding.activity = this
    }

    fun onSearchClick() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(binding.navHost)
        return when (item.itemId) {
            R.id.top_5 -> {
                if (navController.currentDestination?.id != R.id.navigation_top5) {
                    navController.navigate(R.id.action_top5)
                }
                true
            }
            R.id.genre -> {
                if (navController.currentDestination?.id != R.id.navigation_genre) {
                    navController.navigate(R.id.action_genre)
                }
                true
            }
            R.id.all -> {
                if (navController.currentDestination?.id != R.id.navigation_browse) {
                    navController.navigate(R.id.action_browse)
                }

                true
            }
            R.id.search -> {
                onSearchClick()
                true
            }
            else -> false
        }
    }

    override fun onResume() {
        super.onResume()

    }
}