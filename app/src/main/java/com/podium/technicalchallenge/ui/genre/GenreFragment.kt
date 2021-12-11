package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearSnapHelper
import com.podium.technicalchallenge.databinding.FragmentGenreBinding

class GenreFragment : Fragment() {
    private val viewModel: GenreViewModel by activityViewModels()
    private val snapHelper = LinearSnapHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentGenreBinding.inflate(inflater).also {
            it.viewModel = viewModel
            snapHelper.attachToRecyclerView(it.genreRecycler)
        }.root
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}