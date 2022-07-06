package com.example.sicpa_assessment_android.services

import com.example.sicpa_assessment_android.api.ArticleApi
import com.example.sicpa_assessment_android.models.MostPopularArticle
import com.example.sicpa_assessment_android.models.Result
import com.example.sicpa_assessment_android.models.SearchArticle
import javax.inject.Inject

class ArticleStore @Inject constructor(
    private val api: ArticleApi
) {

    suspend fun fetchMostViewedArticles(): Result<MostPopularArticle> {
        val response = api.fetchMostViewedArticle()
        val data = response.body()
        return if (response.isSuccessful && data != null) {
            Result.Success(data)
        } else {
            Result.Failure(Throwable())
        }
    }

    suspend fun fetchMostSharedArticles(): Result<MostPopularArticle> {
        val response = api.fetchMostSharedArticle()
        val data = response.body()
        return if (response.isSuccessful && data != null) {
            Result.Success(data)
        } else {
            Result.Failure(Throwable())
        }
    }

    suspend fun fetchMostEmailedArticles(): Result<MostPopularArticle> {
        val response = api.fetchMostEmailedArticle()
        val data = response.body()
        return if (response.isSuccessful && data != null) {
            Result.Success(data)
        } else {
            Result.Failure(Throwable())
        }
    }

    suspend fun getArticles(searchArticle: String): Result<List<SearchArticle>> {
        val response = api.searchArticle(searchArticle)
        val data = response.body()
        return if (response.isSuccessful && data != null) {
            Result.Success(data)
        } else {
            Result.Failure(Throwable())
        }
    }
}