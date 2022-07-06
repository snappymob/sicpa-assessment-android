package com.example.sicpa_assessment_android.ui.most_shared

import com.example.sicpa_assessment_android.models.MostPopularArticle
import com.example.sicpa_assessment_android.models.Result
import com.example.sicpa_assessment_android.services.ArticleStore
import com.example.sicpa_assessment_android.shared.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MostSharedArticleViewModel @Inject constructor(
    private val articleStore: ArticleStore
) : StatefulViewModel<MostPopularArticle>() {

    init {
        proceedToLoad()
    }

    override suspend fun load(): Result<MostPopularArticle> {
        return articleStore.fetchMostSharedArticles()
    }
}