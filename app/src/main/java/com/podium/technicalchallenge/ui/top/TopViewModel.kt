package com.podium.technicalchallenge.ui.top

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.MovieSearchQuery
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.api.Repo
import com.podium.technicalchallenge.api.Result
import com.podium.technicalchallenge.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding

class TopViewModel : ViewModel() {

    init {
        fetchMovies()
    }

    val movies = ObservableArrayList<MovieSearchQuery.Movie>()

    private fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().topMovies(6)
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<MovieSearchQuery.Data?> -> {
                    val resultMovies = result.data?.movies?.filterNotNull()
                    if (resultMovies != null) {
                        withContext(Dispatchers.Main) {
                            movies.addAll(resultMovies)
                        }
                    }
                }
                else -> {
                    Log.e(TAG, "topMovies= $result")
                }
            }
        }
    }

    fun getTop5Binding(): ItemBinding<MovieSearchQuery.Movie> {
        return ItemBinding.of(BR.movie, R.layout.recycler_top_movie)
    }
}