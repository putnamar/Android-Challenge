package com.podium.technicalchallenge.ui.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.podium.technicalchallenge.databinding.FragmentTopMoviesBinding

class TopFragment : Fragment() {
    private val viewModel: TopViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTopMoviesBinding.inflate(inflater).also {
            it.viewModel = viewModel
        }.root
    }

    companion object {
        fun newInstance() = TopFragment()
    }
}