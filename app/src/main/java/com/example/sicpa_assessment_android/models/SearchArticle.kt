package com.example.sicpa_assessment_android.models

import com.squareup.moshi.Json

data class SearchArticle(
    @Json(name = "response")
    val searchResult: List<SearchResult>
)

data class SearchResult(
    @Json(name = "docs")
    val searchArticleList: SearchArticleList
)

class SearchArticleList: ArrayList<SearchResultArticles>()

data class SearchResultArticles(
    @Json(name = "snippet")
    val title: String?
)