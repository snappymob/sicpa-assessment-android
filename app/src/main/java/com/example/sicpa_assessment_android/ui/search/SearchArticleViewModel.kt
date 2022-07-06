package com.example.sicpa_assessment_android.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sicpa_assessment_android.models.Result
import com.example.sicpa_assessment_android.models.SearchArticle
import com.example.sicpa_assessment_android.models.State
import com.example.sicpa_assessment_android.services.ArticleStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArticleViewModel @Inject constructor(
    private val articleStore: ArticleStore
) :ViewModel() {

    private val _state = MutableStateFlow<State<SearchArticle>>(State.Initial)
    val state = _state.asStateFlow()

    fun getArticleList(searchResult: String) = viewModelScope.launch {
        _state.emit(State.Loading)

//        when (val result = articleStore.getArticles(searchResult)) {
//            is Result.Success -> _state.emit(State.Loaded())
//            is Result.Failure -> _state.emit(State.LoadingFailed(result.error))
//        }
    }
//) : StatefulViewModel<List<SearchArticle>>() {

//    init {
//        proceedToLoad()
//    }
//
//    override suspend fun load(): Result<List<SearchArticle>> {
//        suspend fun getArticleList(searchResult: String) {
//            articleStore.getArticles(searchResult)
//        }
//    }
}