package com.rrg.sicpa_test.api

import com.rrg.sicpa_test.models.primary.ArticleSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/v2/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") searchQuery: String,
        @Query("page") page: Int
    ): Response<ArticleSearch>

    @GET("mostpopular/v2/{type}/{period}.json")
    suspend fun getPopularArticles(
        @Path("type") articlesType: String,
        @Path("period") timePeriod: String
    ): Response<ArticleSearch>
}