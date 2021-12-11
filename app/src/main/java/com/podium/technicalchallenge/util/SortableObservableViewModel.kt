package com.podium.technicalchallenge.util

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.podium.technicalchallenge.BR
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.search.SearchRepo

abstract class SortableObservableViewModel : ObservableViewModel() {
    protected val searchRepo = SearchRepo.getInstance()
    protected val SORT_ASC = "ASC"
    protected val SORT_DESC = "DESC"
    protected val DISP_VOTE_AVERAGE = "Vote Average"
    protected val COLUMN_VOTE_AVERAGE = "voteAverage"
    protected val DISP_VOTE_COUNT = "Vote Count"
    protected val COLUMN_VOTE_COUNT = "voteCount"
    protected val DISP_POPULARITY = "Popularity"
    protected val COLUMN_POPULARITY = DISP_POPULARITY.lowercase()
    protected val DISP_BUDGET = "Budget"
    protected val COLUMN_BUDGET = DISP_BUDGET.lowercase()
    protected var sortColumn = "voteAverage"
    protected var sortOrder = false

    private val propertyChanged: Observable.OnPropertyChangedCallback =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.searchTerms -> {
                        searchTermsChanged()
                    }
                }
            }
        }

    init {
        searchRepo.addOnPropertyChangedCallback(propertyChanged)
    }

    override fun onCleared() {
        super.onCleared()
        searchRepo.removeOnPropertyChangedCallback(propertyChanged)
    }

    protected abstract fun invalidateOrder()
    protected abstract fun searchTermsChanged()

    @Bindable
    var displayOrder: String = SORT_DESC
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.displayOrder)
            }
        }


    @Bindable
    var displayColumn: String = DISP_VOTE_AVERAGE
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.displayColumn)
            }
        }

    fun onCategoryClick(v: View) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.popup_category, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.category_vote_average -> {
                    sortColumn = COLUMN_VOTE_AVERAGE
                    displayColumn = DISP_VOTE_AVERAGE
                }
                R.id.category_vote -> {
                    sortColumn = COLUMN_VOTE_COUNT
                    displayColumn = DISP_VOTE_COUNT
                }
                R.id.category_popularity -> {
                    sortColumn = COLUMN_POPULARITY
                    displayColumn = DISP_POPULARITY
                }
                R.id.category_budget -> {
                    sortColumn = COLUMN_BUDGET
                    displayColumn = DISP_BUDGET
                }
            }
            invalidateOrder()
            true
        }

        popup.show()
    }

    fun onOrderClick(v: View) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.popup_sort, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.category_ascending -> {
                    sortOrder = true
                    displayOrder = SORT_ASC
                }
                R.id.category_descending -> {
                    sortOrder = false
                    displayOrder = SORT_DESC
                }
            }
            invalidateOrder()
            true
        }

        popup.show()
    }
}