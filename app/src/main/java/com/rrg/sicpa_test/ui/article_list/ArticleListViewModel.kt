package com.rrg.sicpa_test.ui.article_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rrg.sicpa_test.service.ApiService
import com.rrg.sicpa_test.service.ArticleListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    var searchQuery = ""

    private val pageSize = 10
    val pagedArticles = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = pageSize * 2
        ),
        pagingSourceFactory = { ArticleListPagingSource(apiService, searchQuery) }
    ).flow.cachedIn(viewModelScope)
}