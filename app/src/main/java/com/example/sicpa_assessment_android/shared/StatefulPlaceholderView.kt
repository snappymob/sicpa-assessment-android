package com.example.sicpa_assessment_android.shared

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.sicpa_assessment_android.R
import com.example.sicpa_assessment_android.databinding.LayoutStatefulPlaceholderViewBinding
import com.example.sicpa_assessment_android.models.State

class StatefulPlaceholderView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        sharedInit()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        sharedInit()
    }

    private var onRetry: (() -> Unit)? = null

    fun onRetry(onRetry: () -> Unit) {
        this.onRetry = onRetry
    }

    private lateinit var binding: LayoutStatefulPlaceholderViewBinding

    private fun sharedInit() {
        binding = LayoutStatefulPlaceholderViewBinding.inflate(
            LayoutInflater.from(context), this, true
        )
        binding.retryButton.setOnClickListener {
            onRetry?.invoke()
        }
    }

    fun bind(state: State<*>) = with(binding) {
        when (state) {
            is State.Initial -> {
                isVisible = true
                progressBar.isVisible = false

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = false
                errorInfoTextView.isVisible = false
                retryButton.isVisible = false
            }
            is State.Loading -> {
                isVisible = true
                progressBar.isVisible = true

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = false
                errorInfoTextView.isVisible = false
                retryButton.isVisible = false
            }
            is State.LoadingFailed -> {
                isVisible = true
                progressBar.isVisible = false

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = true
                errorInfoTextView.isVisible = true
                retryButton.isVisible = true

                errorTitleTextView.text =
                    context.getString(R.string.error_generic_title)
                errorInfoTextView.text =
                    context.getString(R.string.error_generic_description)
            }
            is State.RetryingLoad -> {
                isVisible = true
                progressBar.isVisible = true

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = false
                errorInfoTextView.isVisible = false
                retryButton.isVisible = false
            }
            is State.Loaded -> {
                isVisible = false
                progressBar.isVisible = false

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = false
                errorInfoTextView.isVisible = false
                retryButton.isVisible = false
            }
            is State.ManualReloading -> {
                isVisible = false
                progressBar.isVisible = false

                errorImageView.isVisible = false
                errorTitleTextView.isVisible = false
                errorInfoTextView.isVisible = false
                retryButton.isVisible = false
            }
            // Do nothing since this placeholder will not be used for next page loading screens.
            is State.LoadingNextPage -> Unit
        }
    }
}
