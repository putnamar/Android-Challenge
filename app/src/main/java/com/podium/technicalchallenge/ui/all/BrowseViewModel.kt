package com.podium.technicalchallenge.ui.all

import android.util.Log
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import com.podium.technicalchallenge.GetMoviesPageQuery
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.Result
import com.podium.technicalchallenge.util.SortableObservableViewModel
import com.podium.technicalchallenge.util.TAG
import java.util.*

class BrowseViewModel : SortableObservableViewModel() {
    private val PAGE_SIZE = 12
    private var pagingDataCallback: LoadPagingData? = null

    val pagedList: Pager<Int, GetMoviesPageQuery.Movie>

    val diff: DiffUtil.ItemCallback<GetMoviesPageQuery.Movie> =
        object : DiffUtil.ItemCallback<GetMoviesPageQuery.Movie>() {
            override fun areItemsTheSame(
                oldItem: GetMoviesPageQuery.Movie,
                newItem: GetMoviesPageQuery.Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GetMoviesPageQuery.Movie,
                newItem: GetMoviesPageQuery.Movie
            ): Boolean {
                return Objects.equals(oldItem, newItem)
            }
        }

    val pagingAdapter = MovieAdapter(diff)

    init {
        pagedList = Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            object : PagingSource<Int, GetMoviesPageQuery.Movie>() {
                override fun getRefreshKey(state: PagingState<Int, GetMoviesPageQuery.Movie>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        val anchorPage = state.closestPageToPosition(anchorPosition)
                        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                    }
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetMoviesPageQuery.Movie> {
                    val safeKey = params.key ?: 0
                    val list = fetchMoviePage(safeKey, params.loadSize)
                        ?: return LoadResult.Error(java.lang.Exception("Error loading page"))

                    return LoadResult.Page(
                        data = list,
                        prevKey = if (safeKey == 0) null else (safeKey - params.loadSize),
                        nextKey = if (safeKey >= 50 - params.loadSize) null else (safeKey + params.loadSize),
                        itemsBefore = safeKey,
                    )
                }
            }
        }
    }

    fun setPagingDataCallback(callback: LoadPagingData) {
        pagingDataCallback = callback
    }

    override fun invalidateOrder() {
        pagingDataCallback?.invalidatePagingData()
    }

    private suspend fun fetchMoviePage(
        start: Int,
        size: Int
    ): List<GetMoviesPageQuery.Movie>? {
        val result = try {
            Repo.getInstance().getMoviePage(start, size, sortColumn, sortOrder)
        } catch (e: Exception) {
            Result.Error(e)
        }
        return when (result) {
            is Result.Success<GetMoviesPageQuery.Data?> -> {
                result.data?.movies?.filterNotNull()
            }
            else -> {
                Log.e(TAG, "fetchMoviePage= $result")
                null
            }
        }
    }

}