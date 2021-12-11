package com.podium.technicalchallenge.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.podium.technicalchallenge.databinding.FragmentBrowseBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface LoadPagingData {
    fun invalidatePagingData()
}

class BrowseFragment : Fragment(), LoadPagingData {
    private val viewModel: BrowseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowseBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.browseRecycler.adapter = viewModel.pagingAdapter

        invalidatePagingData()
        viewModel.setPagingDataCallback(this)

        return binding.root
    }

    override fun invalidatePagingData() {
        viewModel.pagingAdapter.submitData(lifecycle, PagingData.empty())
        lifecycleScope.launch {
            viewModel.pagedList.flow.collectLatest { pagingData ->
                viewModel.pagingAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        fun newInstance() = BrowseFragment()
    }
}