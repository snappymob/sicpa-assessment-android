package com.rrg.sicpa_test.ui.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rrg.sicpa_test.databinding.LayoutLoadingFooterBinding

class LoadingFooterAdapter(private val retry:() ->Unit): LoadStateAdapter<LoadingFooterAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(LayoutLoadingFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class ViewHolder(private val binding: LayoutLoadingFooterBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(loadState: LoadState){
            binding.apply {
                errorTextView.isVisible = loadState is LoadState.Error
                retryButton.isVisible = loadState is LoadState.Error
                progressBar.isVisible = loadState is LoadState.Loading

                retryButton.setOnClickListener {
                    retry.invoke()
                }
            }
        }
    }
}