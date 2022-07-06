package com.example.sicpa_assessment_android.ui.most_shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sicpa_assessment_android.databinding.FragmentMostSharedArticleBinding
import com.example.sicpa_assessment_android.epoxy.articleResult
import com.example.sicpa_assessment_android.models.State
import com.example.sicpa_assessment_android.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MostSharedArticleFragment : BaseFragment<FragmentMostSharedArticleBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMostSharedArticleBinding
        get() = FragmentMostSharedArticleBinding::inflate

    private val viewModel: MostSharedArticleViewModel by viewModels()

    override fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch { observeState() }
    }

    private suspend fun observeState() {
        viewModel.state.collect { state ->
            binding.statefulPlaceholderView.bind(state)
            when (state) {
                is State.Loaded -> {
                    binding.recyclerView.withModels {
                        state.data.articleList.forEach { article ->
                            articleResult {
                                id(article.id)
                                title(article.title)
                                date(article.publicationDate)
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}