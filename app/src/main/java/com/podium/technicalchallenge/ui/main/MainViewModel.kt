package com.podium.technicalchallenge.ui.main

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.Bindable
import androidx.navigation.Navigation
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.search.SearchRepo
import com.podium.technicalchallenge.util.ObservableViewModel

class MainViewModel : ObservableViewModel() {
    val searchRepo = SearchRepo.getInstance()

    @Bindable
    var expanded = false
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.expanded)
                searchRepo.searchTerms = null
            }
        }

    val searchQueryListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            searchRepo.searchTerms = query
            if (searchRepo.searchTerms.isNullOrEmpty()) {
                expanded = false
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }
    fun onSearchClick() {
        expanded = !expanded
    }

    fun onItemSelected(navHost: View, item: MenuItem): Boolean {
        val navController = Navigation.findNavController(navHost)

        return when (item.itemId) {
            R.id.top_5 -> {
                if (navController.currentDestination?.id != R.id.navigation_top5) {

                    navController.navigate(R.id.action_top5)
                }
                expanded = false
                searchRepo.searchTerms = null
                true
            }
            R.id.genre -> {
                if (navController.currentDestination?.id != R.id.navigation_genre) {
                    navController.navigate(R.id.action_genre)
                }
                expanded = false
                searchRepo.searchTerms = null
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
                false
            }
            else -> false
        }
    }
}