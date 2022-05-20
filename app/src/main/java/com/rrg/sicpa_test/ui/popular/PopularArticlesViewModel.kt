package com.rrg.sicpa_test.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrg.sicpa_test.models.primary.Results
import com.rrg.sicpa_test.core.Result
import com.rrg.sicpa_test.core.State
import com.rrg.sicpa_test.models.keys.ArticleTypes
import com.rrg.sicpa_test.models.keys.HomeKeys
import com.rrg.sicpa_test.models.keys.TimePeriodKeys
import com.rrg.sicpa_test.models.primary.ArticleSearch
import com.rrg.sicpa_test.service.ApiService
import com.rrg.sicpa_test.utils.processNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularArticlesViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val state = MutableLiveData<State<ArticleSearch>>(State.Loading)

    var pageType: HomeKeys = HomeKeys.SearchArticles
    var timePeriod : TimePeriodKeys = TimePeriodKeys.Thirty
    private lateinit var articleType: ArticleTypes

    fun proceedToLoad() {
        state.value = State.Loading //reset state

        articleType = when (pageType) {
            HomeKeys.MostViewed -> {
                ArticleTypes.viewed
            }
            HomeKeys.MostShared -> {
                ArticleTypes.shared
            }
            else -> {
                ArticleTypes.emailed
            }
        }
        fetchArticles()
    }

    private fun fetchArticles() = viewModelScope.launch {
        val result = apiService.searchPopularArticles(articleType, timePeriod)
        state.processNetworkResult(result)
    }
}