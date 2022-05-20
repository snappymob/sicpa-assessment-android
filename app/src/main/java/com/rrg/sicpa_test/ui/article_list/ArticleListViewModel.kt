package com.rrg.sicpa_test.ui.article_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rrg.sicpa_test.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private var searchQuery = MutableLiveData("")

    private val pageSize = 10

    fun updateSearchQuery(query:String){
        searchQuery.value = query
    }

    val pagedArticles = searchQuery.switchMap { currentQuery ->
        apiService.getPagedNewsArticles(currentQuery,pageSize).cachedIn(viewModelScope)
    }
}