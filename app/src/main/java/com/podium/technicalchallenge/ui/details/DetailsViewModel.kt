package com.podium.technicalchallenge.ui.details

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.GetMovieQuery
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.Result
import com.podium.technicalchallenge.util.ObservableViewModel
import com.podium.technicalchallenge.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ObservableViewModel() {

    @Bindable
    var originalLanguage: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.originalLanguage)
            }
        }

    @Bindable
    var originalTitle: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.originalTitle)
            }
        }

    @Bindable
    var overview: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.overview)
            }
        }

    @Bindable
    var popularity: Double? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.popularity)
            }
        }

    @Bindable
    var posterPath: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.posterPath)
            }
        }

    @Bindable
    var releaseDate: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.releaseDate)
            }
        }

    @Bindable
    var voteAverage: Double? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.voteAverage)
            }
        }

    @Bindable
    var voteCount: Int? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.voteCount)
            }
        }

    @Bindable
    var title: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.title)
            }
        }

    @Bindable
    var budget: Float? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.budget)
            }
        }

    @Bindable
    var runtime: Int? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.runtime)
            }
        }

    @Bindable
    var genres: List<String>? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.genres)
            }
        }
    @Bindable
    var cast: List<GetMovieQuery.Cast>? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.cast)
            }
        }
    @Bindable
    var director: GetMovieQuery.Director? = null
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.director)
            }
        }

    fun loadMovie(movieId: Int?) {
        movieId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = try {
                    Repo.getInstance().getMovie(movieId)
                } catch (e: Exception) {
                    Result.Error(e)
                }
                when (result) {
                    is Result.Success<GetMovieQuery.Data?> -> {
                        result.data?.movie?.let { movie ->
                            originalLanguage = movie.originalLanguage
                            originalTitle = movie.originalTitle
                            overview = movie.overview
                            popularity = movie.popularity
                            posterPath = movie.posterPath
                            releaseDate = movie.releaseDate
                            voteAverage = movie.voteAverage
                            voteCount = movie.voteCount
                            title = movie.title
                            budget = movie.budget.toFloat()
                            runtime = movie.runtime
                            genres = movie.genres
                            cast = movie.cast
                            director = movie.director
                        }
                    }
                    else -> {
                        Log.e(TAG, "topMovies= $result")
                    }
                }
            }
        }
    }
}