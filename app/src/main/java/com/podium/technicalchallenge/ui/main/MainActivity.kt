package com.podium.technicalchallenge.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.ActivityMainBinding
import com.podium.technicalchallenge.databinding.NavigationRailFabBinding
import com.podium.technicalchallenge.search.SearchRepo
import com.podium.technicalchallenge.util.showInputMethod


class MainActivity : AppCompatActivity() {
    // TODO: Common movie class
    // TODO: Database
    // TODO: Save User Settings

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val searchRepo = SearchRepo.getInstance()

    private val propertyChanged: Observable.OnPropertyChangedCallback =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.expanded -> {
                        if (viewModel.expanded) {
                            binding.searchView.requestFocus()
                            binding.root.postDelayed({ showInputMethod(binding.searchView.findFocus()) }, 250)

                            val navController = Navigation.findNavController(binding.navHost)

                            if (navController.currentDestination?.id != R.id.navigation_browse) {
                                val navBar = binding.navigationViewMobile ?: binding.navigationViewTablet
                                navController.navigate(R.id.action_browse)
                                navBar?.selectedItemId = R.id.navigation_browse
                                navBar?.selectedItemId = R.id.all
                            }
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

        var closeButton = binding.searchView.findViewById<View>(R.id.search_close_btn)
        closeButton.setOnClickListener {
            searchRepo.searchTerms = null
            viewModel.expanded = false
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