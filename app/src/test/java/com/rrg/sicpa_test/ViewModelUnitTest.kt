package com.rrg.sicpa_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rrg.sicpa_test.api.Api
import com.rrg.sicpa_test.core.AppError
import com.rrg.sicpa_test.core.Result
import com.rrg.sicpa_test.core.State
import com.rrg.sicpa_test.models.keys.ArticleTypes
import com.rrg.sicpa_test.models.keys.HomeKeys
import com.rrg.sicpa_test.models.keys.TimePeriodKeys
import com.rrg.sicpa_test.models.primary.ArticleSearch
import com.rrg.sicpa_test.service.ApiService
import com.rrg.sicpa_test.service.ArticleListPagingSource
import com.rrg.sicpa_test.ui.article_list.ArticleListViewModel
import com.rrg.sicpa_test.ui.popular.PopularArticlesViewModel
import com.rrg.sicpa_test.utils.processNetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.times

@ExperimentalCoroutinesApi
class ViewModelUnitTest {

    companion object {
        val mockData = ArticleSearch()
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var popularArticlesViewModel: PopularArticlesViewModel
    private lateinit var articlesViewModel: ArticleListViewModel
    private lateinit var apiService: ApiService
    private lateinit var api: Api
    private lateinit var pagingSource: ArticleListPagingSource

    @Before
    fun init(){
        apiService = Mockito.mock(ApiService::class.java)
        api = Mockito.mock((Api::class.java))
        popularArticlesViewModel = PopularArticlesViewModel(apiService)
        articlesViewModel = ArticleListViewModel(apiService)
        pagingSource = ArticleListPagingSource(api, "")
    }

    @Test
    fun `initial state of viewModel should be loading`() {
        assert(popularArticlesViewModel.state.value is State.Loading)
    }

    @Test
    fun `error responses from network call should set state to loading-failed`() {
        `initial state of viewModel should be loading`()
        popularArticlesViewModel.apply {
            state.processNetworkResult(Result.Failure(AppError(AppError.Code.ServerError)))
        }
        assert(popularArticlesViewModel.state.value is State.LoadingFailed)
    }

    @Test
    fun `valid responses from network call should set state to loaded`() {
        `initial state of viewModel should be loading`()
        popularArticlesViewModel.apply {
            state.processNetworkResult(Result.Success(any()))
        }
        assert(popularArticlesViewModel.state.value is State.Loaded)
    }

    @Test
    fun `viewModel should reset to loading state for each network call`() = runTest {
        popularArticlesViewModel.apply {
            pageType = HomeKeys.MostViewed
            timePeriod = TimePeriodKeys.Thirty
            proceedToLoad()
        }
        assert(popularArticlesViewModel.state.value is State.Loading)
    }


    @Test
    fun `viewModel has loaded state when service returns success`() = runTest {
        `when`(apiService.searchPopularArticles(any(), any())).thenReturn(
            Result.Success(mockData)
        )
        popularArticlesViewModel.apply {
            pageType = HomeKeys.MostViewed
            timePeriod = TimePeriodKeys.Thirty
            proceedToLoad()
        }
        advanceUntilIdle()

        assert(popularArticlesViewModel.state.value is State.Loaded)
    }

    @Test
    fun `viewModel has loading failed state when service returns success`() = runTest {
        `when`(apiService.searchPopularArticles(any(), any())).thenReturn(
            Result.Failure(AppError(AppError.Code.ServerError))
        )
        popularArticlesViewModel.apply {
            pageType = HomeKeys.MostViewed
            timePeriod = TimePeriodKeys.Thirty
            proceedToLoad()
        }
        advanceUntilIdle()

        assert(popularArticlesViewModel.state.value is State.LoadingFailed)
    }

    @Test
    fun `calling service methods for popular articles`() = runTest {
        `when`(apiService.searchPopularArticles(any(), any())).thenReturn(
            Result.Failure(AppError(AppError.Code.ServerError))
        )
        popularArticlesViewModel.apply {
            pageType = HomeKeys.MostViewed
            timePeriod = TimePeriodKeys.Thirty
            proceedToLoad()
        }
        advanceUntilIdle()
        Mockito.verify(apiService, times(1)).searchPopularArticles(ArticleTypes.viewed, TimePeriodKeys.Thirty)
        Mockito.verify(apiService, times(0)).searchNewsArticles("", 1)
    }
}