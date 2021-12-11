package com.podium.technicalchallenge.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.podium.technicalchallenge.databinding.FragmentMovieDetailsBinding

class DetailsFragment : BottomSheetDialogFragment() {

    private val viewModel: DetailsViewModel by activityViewModels()
    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieId = arguments?.getInt(ARG_MOVIE_ID)

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

