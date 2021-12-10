package com.podium.technicalchallenge.search

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.podium.technicalchallenge.BR

class SearchRepo : BaseObservable() {

    @Bindable
    var searchTerms: String? = null
        set(value) {
            if (field != value) {
                field = value
                field.orEmpty().ifBlank { field = null }
                notifyPropertyChanged(BR.searchTerms)
            }
        }

    companion object {
        private var INSTANCE: SearchRepo? = null
        fun getInstance() = INSTANCE
            ?: SearchRepo().also {
                INSTANCE = it
            }
    }

}