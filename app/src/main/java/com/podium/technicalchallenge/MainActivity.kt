package com.podium.technicalchallenge

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationBarView
import com.podium.technicalchallenge.databinding.ActivityMainBinding
import com.podium.technicalchallenge.ui.dashboard.DashboardFragment
import com.podium.technicalchallenge.util.TAG

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(binding.root)

        binding.fragment = this
    }

    fun onSearchClick() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.top_5 -> {
                Navigation.findNavController(binding.navHost).navigate(R.id.navigation_top5)
                true
            }
            R.id.genre -> {
                Navigation.findNavController(binding.navHost).navigate(R.id.navigation_genre)
                true
            }
            R.id.all -> {
                Navigation.findNavController(binding.navHost).navigate(R.id.navigation_browse)
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