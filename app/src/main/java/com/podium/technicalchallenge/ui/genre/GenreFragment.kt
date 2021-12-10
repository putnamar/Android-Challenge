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
    private val viewModel: GenreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentGenreBinding.inflate(inflater).also {
            it.viewModel = viewModel
        }.root
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}