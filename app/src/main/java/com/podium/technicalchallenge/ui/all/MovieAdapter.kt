package com.podium.technicalchallenge.ui.all

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.GetMoviesPageQuery
import com.podium.technicalchallenge.databinding.RecyclerBrowseBinding
import com.podium.technicalchallenge.util.layoutInflater

class MovieViewHolder(var binding: RecyclerBrowseBinding) : RecyclerView.ViewHolder(binding.root)


class MovieAdapter(diffCallback: DiffUtil.ItemCallback<GetMoviesPageQuery.Movie>) :
    PagingDataAdapter<GetMoviesPageQuery.Movie, MovieViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(RecyclerBrowseBinding.inflate(parent.context.layoutInflater))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.movie = item
    }
}