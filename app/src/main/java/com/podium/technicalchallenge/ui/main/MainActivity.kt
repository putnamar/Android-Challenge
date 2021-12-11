package com.podium.technicalchallenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.ActivityMainBinding
import com.podium.technicalchallenge.databinding.NavigationRailFabBinding
import com.podium.technicalchallenge.util.showInputMethod


class MainActivity : AppCompatActivity() {
    // TODO: Search View
    // TODO: Adjust gridlayoutmanagers for screen size
    // TODO: Make top count dependent on screen size
    // TODO: Common movie class
    // TODO: Database?

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val propertyChanged: Observable.OnPropertyChangedCallback =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.expanded -> {
                        if (viewModel.expanded) {
                            binding.searchView.requestFocus()
                            binding.root.postDelayed({ showInputMethod(binding.searchView.findFocus()) }, 250)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel

        binding.navigationViewTablet?.headerView?.also { headerView ->
            DataBindingUtil.bind<NavigationRailFabBinding>(headerView)?.viewModel = viewModel
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.addOnPropertyChangedCallback(propertyChanged)
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeOnPropertyChangedCallback(propertyChanged)
    }
}