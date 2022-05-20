package com.rrg.sicpa_test.service

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.rrg.sicpa_test.api.Api
import com.rrg.sicpa_test.core.NetworkRequestManager
import com.rrg.sicpa_test.core.Result
import com.rrg.sicpa_test.models.keys.ArticleTypes
import com.rrg.sicpa_test.models.keys.TimePeriodKeys
import com.rrg.sicpa_test.models.primary.ArticleSearch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(
    private val networkRequestManager: NetworkRequestManager,
    private val api: Api
): BaseApiService {

    override suspend fun searchNewsArticles(searchQuery: String, pageNumber: Int): Response<ArticleSearch> {
        return  api.searchArticles(searchQuery,pageNumber)
    }

    override suspend fun searchPopularArticles(articleType: ArticleTypes, timePeriod: TimePeriodKeys): Result<ArticleSearch>{
        return networkRequestManager.apiRequest {
            api.getPopularArticles(articleType.name, timePeriod.value.toString())
        }
    }

    fun getPagedNewsArticles(searchQuery: String, pageSize:Int) = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = pageSize * 1
        ),
        pagingSourceFactory = { ArticleListPagingSource(api, searchQuery) }
    ).liveData
}