package com.podium.technicalchallenge.ui.genre

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.*
import com.podium.technicalchallenge.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding

class GenreViewModel : ViewModel() {
    val genres = ObservableArrayList<String>()
    val genreBinding: ItemBinding<String>
    val genreMovieBinding: ItemBinding<GenreSearchQuery.Movie> =
        ItemBinding.of(BR.movie, R.layout.recycler_movie)
    private val moviesByGenre: ObservableArrayMap<String, ObservableArrayList<GenreSearchQuery.Movie>> =
        ObservableArrayMap()

    init {
        fetchGenres()
        genreBinding =
            ItemBinding.of<String>(BR.genre, R.layout.recycler_genre).bindExtra(BR.viewModel, this)

    }


    fun getMoviesByGenre(genre: String): ObservableArrayList<GenreSearchQuery.Movie> {
        if (!moviesByGenre.containsKey(genre)) {
            moviesByGenre[genre] = ObservableArrayList()

            viewModelScope.launch(Dispatchers.IO) {
                val result = try {
                    Repo.getInstance().getMoviesByGenre(genre)
                } catch (e: Exception) {
                    Result.Error(e)
                }
                when (result) {
                    is Result.Success<GenreSearchQuery.Data?> -> {
                        val movies = result.data?.movies?.filterNotNull()
                        if (movies != null) {
                            withContext(Dispatchers.Main) {
                                moviesByGenre[genre]?.addAll(movies)
                            }
                        }
                    }
                    else -> {
                        Log.e(TAG, "genres= $result")
                    }
                }
            }
        }
        return moviesByGenre[genre] ?: ObservableArrayList()

    }

    private fun fetchGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getGenres()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<GetAllGenresQuery.Data?> -> {
                    withContext(Dispatchers.Main) {
                        genres.addAll(result.data?.genres!!.sorted())
                    }
                }
                else -> {
                    Log.e(TAG, "genres= $result")
                }
            }
        }
    }
}