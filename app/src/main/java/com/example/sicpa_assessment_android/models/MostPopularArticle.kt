package com.example.sicpa_assessment_android.models

import com.squareup.moshi.Json

data class MostPopularArticle(
    @Json(name = "results")
    val articleList: List<Articles>
)

data class Articles(
    val id: Long,
    val title: String?,
    @Json(name = "published_date")
    val publicationDate: String?
)