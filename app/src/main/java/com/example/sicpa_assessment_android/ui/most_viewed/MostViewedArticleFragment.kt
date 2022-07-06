package com.example.sicpa_assessment_android.ui.most_viewed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sicpa_assessment_android.databinding.FragmentMostViewedArticleBinding
import com.example.sicpa_assessment_android.epoxy.articleResult
import com.example.sicpa_assessment_android.models.State
import com.example.sicpa_assessment_android.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MostViewedArticleFragment : BaseFragment<FragmentMostViewedArticleBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMostViewedArticleBinding
        get() = FragmentMostViewedArticleBinding::inflate

    private val viewModel: MostViewedArticleViewModel by viewModels()

    override fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch { observeState() }

//        addRightCancelableDrawable(binding.searchEditText)
//
//        binding.searchEditText.onRightDrawableClicked {
//            it.text.clear()
//        }
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

//    private fun addRightCancelableDrawable(editText: EditText) {
//        val cancel = resources.getDrawable(R.drawable.ic_baseline_cancel_24)
//        cancel?.setBounds(0, 0, cancel.intrinsicWidth, cancel.intrinsicHeight)
//        editText.setCompoundDrawables(null, null, cancel, null)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
//        this.setOnTouchListener { view, event ->
//            var hasConsumed = false
//            if (view is EditText) {
//                if (event.x >= view.width - view.totalPaddingRight) {
//                    if (event.action == MotionEvent.ACTION_UP) {
//                        onClicked(this)
//                    }
//                    hasConsumed = true
//                }
//            }
//            hasConsumed
//        }
//    }
}