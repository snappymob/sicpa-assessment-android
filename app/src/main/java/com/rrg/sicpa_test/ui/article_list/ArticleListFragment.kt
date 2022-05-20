package com.rrg.sicpa_test.ui.article_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.core.State
import com.rrg.sicpa_test.databinding.FragmentArticleListBinding
import com.rrg.sicpa_test.ui.shared.LoadingFooterAdapter
import com.rrg.sicpa_test.utils.DateTimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListFragment: Fragment(R.layout.fragment_article_list) {

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    private var _binding: FragmentArticleListBinding? = null
    private val binding: FragmentArticleListBinding
        get() = requireNotNull(_binding)
    private val adapter: ArticleListAdapter by lazy {
        ArticleListAdapter(dateTimeFormatter)
    }
    private val viewModel: ArticleListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleListBinding.bind(view)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter.withLoadStateFooter(LoadingFooterAdapter { adapter.refresh() })

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.pagedArticles.collectLatest {
                    adapter.submitData(it)
                }
            }

            searchLayout.textField.editText?.doAfterTextChanged {
                val input = it.toString()
                if(input.length >= 3 || input.isEmpty()){
                    viewModel.searchQuery = input
                    adapter.refresh()
                }
            }

            dataLoadingPlaceholderView.onRetry = {adapter.refresh()}
            // Need to map some paging 3 states to the project's states.
            adapter.addLoadStateListener { loadState ->
                //dataLoadingPlaceholderView.isVisible = loadState.refresh is LoadState.Loading
                if (loadState.refresh is LoadState.NotLoading) {
                    dataLoadingPlaceholderView.isGone = true
                }

                if (loadState.refresh is LoadState.Error) {
                    dataLoadingPlaceholderView.bind(State.LoadingFailed(null))
                }
            }

            swipeToRefreshLayout.setOnRefreshListener {
                dataLoadingPlaceholderView.bind(State.Loading)
                adapter.refresh()
                swipeToRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}