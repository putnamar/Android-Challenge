package com.podium.technicalchallenge.ui.top5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearSnapHelper
import com.podium.technicalchallenge.databinding.FragmentTop5Binding

class Top5Fragment : Fragment() {
    private val viewModel: Top5ViewModel by activityViewModels()
    private var _binding: FragmentTop5Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTop5Binding.inflate(inflater)

        binding.top5Recycler.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayout.VERTICAL
            )
        )

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.top5Recycler)

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = Top5Fragment()
    }
}