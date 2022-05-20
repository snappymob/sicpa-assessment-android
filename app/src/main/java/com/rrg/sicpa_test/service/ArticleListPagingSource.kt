package com.rrg.sicpa_test.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rrg.sicpa_test.api.Api
import com.rrg.sicpa_test.models.primary.Article

class ArticleListPagingSource(
    private val api: Api,
    private val searchQuery: String
) : PagingSource<Int, Article>() {

    companion object {
        private const val START_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: START_INDEX

        try {
            val response = api.searchArticles(searchQuery,position)
            if(response.isSuccessful){
                val data = response.body()?.response?.docs
                return LoadResult.Page(
                    data = data ?: arrayListOf(),
                    prevKey = null, // Only paging forward.
                    nextKey = if (data.isNullOrEmpty()) {
                        null
                    } else {
                        position + 1
                    }
                )
            }else{
                throw Exception()
            }
        } catch (e: Exception) {
            // Catach errors here.
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