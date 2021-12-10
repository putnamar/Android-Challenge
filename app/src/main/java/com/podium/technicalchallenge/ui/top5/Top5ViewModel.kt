package com.podium.technicalchallenge.ui.top5

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.*
import com.podium.technicalchallenge.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding

class Top5ViewModel : ViewModel() {

    init {
        fetchMovies()
    }

    val movies = ObservableArrayList<MovieSearchQuery.Movie>()

    private fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                // TODO: Make it dependent on screen size to ensure it fills up the screen
                Repo.getInstance().topMovies(6)
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<MovieSearchQuery.Data?> -> {
                    withContext(Dispatchers.Main) {
                        movies.addAll(result.data?.movies!!)
                    }
                }
                else -> {
                    Log.e(TAG, "topMovies= " + result)
                }
            }
        }
    }

    fun getTop5Binding(): ItemBinding<MovieSearchQuery.Movie> {
        return ItemBinding.of(BR.movie, R.layout.recycler_top_movie)
    }
}