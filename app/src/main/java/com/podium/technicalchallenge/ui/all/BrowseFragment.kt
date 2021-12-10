package com.podium.technicalchallenge.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.podium.technicalchallenge.databinding.FragmentBrowseBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BrowseFragment : Fragment() {
    private val viewModel: BrowseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowseBinding.inflate(inflater)
        binding.viewModel = viewModel
        val pagingAdapter = MovieAdapter(viewModel.diff)

        binding.browseRecycler.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.pagedList.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }


        return binding.root
    }

    companion object {
        fun newInstance() = BrowseFragment()
    }
}