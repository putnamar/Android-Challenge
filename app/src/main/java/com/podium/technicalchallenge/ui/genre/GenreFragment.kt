package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.databinding.FragmentGenreBinding
import com.podium.technicalchallenge.databinding.FragmentTop5Binding

class GenreFragment : Fragment() {
    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}