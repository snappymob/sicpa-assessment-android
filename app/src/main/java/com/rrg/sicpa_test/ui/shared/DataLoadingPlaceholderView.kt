package com.rrg.sicpa_test.ui.shared

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.core.State
import com.rrg.sicpa_test.databinding.LayoutLoadingPlaceholderViewBinding

class DataLoadingPlaceholderView: ConstraintLayout {

    constructor(context: Context) : super(context) {
        sharedInit()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        sharedInit()
    }

    var onRetry: (() -> Unit)? = null

    private lateinit var binding: LayoutLoadingPlaceholderViewBinding

    private fun sharedInit() {
        val view = inflate(context, R.layout.layout_loading_placeholder_view, this)
        binding = LayoutLoadingPlaceholderViewBinding.bind(view)
        binding.apply {
            retryButton.setOnClickListener {
                onRetry?.invoke()
                onLoading()
            }
        }
    }

    private fun onFailure(){
        binding.apply {
            isVisible = true
            progressBar.isVisible = false
            errorConstraintLayout.isVisible = true
        }
    }

    private fun onDismiss(){
        binding.apply {
            isVisible = false
        }
    }

    private fun onLoading(){
        binding.apply {
            isVisible = true
            progressBar.isVisible = true
            errorConstraintLayout.isVisible = false
        }
    }

    fun bind(state: State<*>) {
        when(state) {
            is State.LoadingFailed -> {onFailure()}
            is State.Loaded -> {onDismiss()}
            is State.Loading -> {onLoading()}
        }
    }
}