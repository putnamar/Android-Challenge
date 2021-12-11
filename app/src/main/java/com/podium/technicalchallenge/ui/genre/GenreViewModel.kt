package com.podium.technicalchallenge.ui.genre

import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableList
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.podium.technicalchallenge.*
import com.podium.technicalchallenge.util.ObservableViewModel
import com.podium.technicalchallenge.util.SortableObservableViewModel
import com.podium.technicalchallenge.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import java.util.*

class GenreViewModel : SortableObservableViewModel() {
    val genres = ObservableArrayList<String>()
    val genreBinding: ItemBinding<String>
    val genreMovieBinding: ItemBinding<GenreSearchQuery.Movie> =
        ItemBinding.of(BR.movie, R.layout.recycler_movie)
    private val moviesByGenre: ObservableArrayMap<String, DiffObservableList<GenreSearchQuery.Movie>> =
        ObservableArrayMap()

    init {
        fetchGenres()
        genreBinding =
            ItemBinding.of<String>(BR.genre, R.layout.recycler_genre).bindExtra(BR.viewModel, this)
    }


    private val diff: DiffUtil.ItemCallback<GenreSearchQuery.Movie> =
        object : DiffUtil.ItemCallback<GenreSearchQuery.Movie>() {
            override fun areItemsTheSame(
                oldItem: GenreSearchQuery.Movie,
                newItem: GenreSearchQuery.Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GenreSearchQuery.Movie,
                newItem: GenreSearchQuery.Movie
            ): Boolean {
                return Objects.equals(oldItem, newItem)
            }
        }
    override fun invalidateOrder() {
        genres.parallelStream().forEach {
            getMoviesByGenre(genre = it, forceReload = true)
        }
    }

    fun getMoviesByGenre(genre: String, forceReload: Boolean = false): ObservableList<GenreSearchQuery.Movie> {
        val list = moviesByGenre[genre] ?: DiffObservableList(diff, true)

        if (!moviesByGenre.containsKey(genre) || forceReload) {
            moviesByGenre[genre] = DiffObservableList(diff, true)

            viewModelScope.launch(Dispatchers.IO) {
                val result = try {
                    Repo.getInstance().getMoviesByGenre(genre, sortColumn, sortOrder)
                } catch (e: Exception) {
                    Result.Error(e)
                }
                when (result) {
                    is Result.Success<GenreSearchQuery.Data?> -> {
                        val movies = result.data?.movies?.filterNotNull()
                        if (movies != null) {
                            withContext(Dispatchers.Main) {
                                list.update(movies)
                            }
                        }
                    }
                    else -> {
                        Log.e(TAG, "genres= $result")
                    }
                }
            }
            // In case it didnt' exist before
            moviesByGenre[genre] = list
        }
        return list

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