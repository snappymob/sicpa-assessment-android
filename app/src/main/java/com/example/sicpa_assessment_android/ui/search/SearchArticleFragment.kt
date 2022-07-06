package com.example.sicpa_assessment_android.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sicpa_assessment_android.databinding.FragmentMostViewedArticleBinding
import com.example.sicpa_assessment_android.databinding.FragmentSearchArticleBinding
import com.example.sicpa_assessment_android.epoxy.articleResult
import com.example.sicpa_assessment_android.models.State
import com.example.sicpa_assessment_android.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArticleFragment : BaseFragment<FragmentSearchArticleBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchArticleBinding
        get() = FragmentSearchArticleBinding::inflate

    private val viewModel: SearchArticleViewModel by viewModels()

    override fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch { observeState() }
    }

    private suspend fun observeState() {
//        viewModel.state.collect { state ->
//            binding.statefulPlaceholderView.bind(state)
//            when (state) {
//                is State.Loaded -> {
//                    binding.recyclerView.withModels {
//                        state.data.forEach { article ->
//                            article.articleList.forEach { articles ->
//                                articleResult {
//                                    title(articles.title)
//                                    date(articles.publicationDate)
//                                }
//                            }
//                        }
//                    }
//                }
//                else -> Unit
//            }
//        }
    }
}