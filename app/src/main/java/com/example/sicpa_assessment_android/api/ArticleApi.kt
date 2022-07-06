package com.example.sicpa_assessment_android.api

import com.example.sicpa_assessment_android.models.MostPopularArticle
import com.example.sicpa_assessment_android.models.SearchArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("mostpopular/v2/viewed/1.json")
    suspend fun fetchMostViewedArticle(): Response<MostPopularArticle>

    @GET("mostpopular/v2/shared/1.json")
    suspend fun fetchMostSharedArticle(): Response<MostPopularArticle>

    @GET("mostpopular/v2/emailed/1.json")
    suspend fun fetchMostEmailedArticle(): Response<MostPopularArticle>

    @GET("search/v2/articlesearch.json?fq=The New York Times")
    suspend fun searchArticle( @Query("fq") query: String): Response<List<SearchArticle>>
}