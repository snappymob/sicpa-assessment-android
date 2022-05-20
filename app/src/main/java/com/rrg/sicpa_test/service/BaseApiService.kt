package com.rrg.sicpa_test.service

import com.rrg.sicpa_test.core.Result
import com.rrg.sicpa_test.models.keys.ArticleTypes
import com.rrg.sicpa_test.models.keys.TimePeriodKeys
import com.rrg.sicpa_test.models.primary.ArticleSearch
import retrofit2.Response

interface BaseApiService {

    suspend fun searchNewsArticles(searchQuery: String, pageNumber: Int): Response<ArticleSearch>

    suspend fun searchPopularArticles(articleType: ArticleTypes, timePeriod: TimePeriodKeys): Result<ArticleSearch>

}