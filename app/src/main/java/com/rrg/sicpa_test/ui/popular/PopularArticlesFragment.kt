package com.rrg.sicpa_test.ui.popular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.core.State
import com.rrg.sicpa_test.databinding.FragmentPopularBinding
import com.rrg.sicpa_test.models.keys.HomeKeys
import com.rrg.sicpa_test.models.primary.ArticleSearch
import com.rrg.sicpa_test.ui.MainActivity
import com.rrg.sicpa_test.utils.DateTimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularArticlesFragment : Fragment(R.layout.fragment_popular), Observer<State<ArticleSearch>> {

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    private var _binding: FragmentPopularBinding? = null
    private val binding: FragmentPopularBinding
        get() = requireNotNull(_binding)
    private val adapter: PopularArticlesAdapter by lazy {
        PopularArticlesAdapter(dateTimeFormatter)
    }
    private val viewModel: PopularArticlesViewModel by viewModels()
    private val args: PopularArticlesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularBinding.bind(view)

        setupToolbar()

        viewModel.apply {
            state.observe(viewLifecycleOwner, this@PopularArticlesFragment)
            pageType = args.articleListType
            proceedToLoad()
        }

        binding.apply {
            dataLoadingPlaceholderView.onRetry = {viewModel.proceedToLoad()}
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)

            swipeToRefreshLayout.setOnRefreshListener {
                viewModel.proceedToLoad()
                swipeToRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setToolbarTitle(
            when (args.articleListType) {
                HomeKeys.MostViewed -> {
                    getString(R.string.most_viewed)
                }
                HomeKeys.MostShared -> {
                    getString(R.string.most_shared)
                }
                else -> {
                    getString(R.string.most_emailed)
                }
            }, true
        )
    }

    override fun onChanged(state: State<ArticleSearch>) {
        binding.dataLoadingPlaceholderView.bind(state)
        if (state is State.Loaded){
            adapter.articleList = state.data.results
        }
    }
}