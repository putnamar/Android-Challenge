package com.podium.technicalchallenge.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.GetMoviesPageQuery
import com.podium.technicalchallenge.databinding.FragmentBrowseBinding
import com.podium.technicalchallenge.databinding.RecyclerBrowseBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BrowseFragment : Fragment() {
    private val viewModel: BrowseViewModel by activityViewModels()
    class MovieViewHolder(var binding: RecyclerBrowseBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    class MovieAdapter(diffCallback: DiffUtil.ItemCallback<GetMoviesPageQuery.Movie>) :
        PagingDataAdapter<GetMoviesPageQuery.Movie, MovieViewHolder>(diffCallback) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MovieViewHolder {
            return MovieViewHolder(RecyclerBrowseBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val item = getItem(position)
            // Note that item may be null. ViewHolder must support binding a
            // null item as a placeholder.
            holder.binding.movie = item
        }
    }
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