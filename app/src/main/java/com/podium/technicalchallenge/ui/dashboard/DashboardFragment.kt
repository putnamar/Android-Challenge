package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationBarView
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), NavigationBarView.OnItemSelectedListener {

    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater)

        binding.fragment = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.top_5 -> {
                Navigation.findNavController(binding.root).navigate(R.id.navigation_top5)
                true
            }
            R.id.genre -> {
                Navigation.findNavController(binding.root).navigate(R.id.navigation_genre)
                true
            }
            R.id.all -> {
                Navigation.findNavController(binding.root).navigate(R.id.navigation_browse)
                true
            }
            R.id.search -> {
                onSearchClick()
                true
            }
            else -> false
        }
    }

    fun onSearchClick() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies()
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}

