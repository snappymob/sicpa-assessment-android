package com.rrg.sicpa_test.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rrg.sicpa_test.api.Api
import com.rrg.sicpa_test.models.primary.Article

class ArticleListPagingSource(
    val apiService: ApiService,
    private val searchQuery: String
) : PagingSource<Int, Article>() {

    companion object {
        private const val START_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: START_INDEX

        try {
            val response = apiService.searchNewsArticles(searchQuery,position).body()!!.response!!.docs
            return LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = if (response.isEmpty()) {
                    null
                } else {
                    position + 1
                }
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}