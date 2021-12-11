package com.podium.technicalchallenge.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.podium.technicalchallenge.databinding.FragmentMovieDetailsBinding

class DetailsFragment : BottomSheetDialogFragment() {
    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieId = arguments?.getInt(ARG_MOVIE_ID)

        val viewModel: DetailsViewModel =
            ViewModelProvider(this).get(movieId.toString(), DetailsViewModel::class.java)
        viewModel.loadMovie(movieId)
        return FragmentMovieDetailsBinding.inflate(inflater).also {
            it.viewModel = viewModel
        }.root
    }


    companion object {
        val ARG_MOVIE_ID = "arg_movie_id"
        fun newInstance() = DetailsFragment()
    }
}

