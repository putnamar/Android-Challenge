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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTop5Binding.inflate(inflater).also {
            it.viewModel = viewModel
        }.root
    }

    companion object {
        fun newInstance() = Top5Fragment()
    }
}