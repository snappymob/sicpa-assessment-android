package com.rrg.sicpa_test.ui.home

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rrg.sicpa_test.databinding.ItemHomeHeaderBinding
import com.rrg.sicpa_test.databinding.ItemHomeMenuBinding
import com.rrg.sicpa_test.models.keys.HomeKeys

sealed class HomeRecyclerViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(private val binding: ItemHomeHeaderBinding) : HomeRecyclerViewHolder(binding){

        fun bind(@StringRes header: Int){
            binding.apply {
                val context = root.context
                headerTextView.text = context.getString(header)
            }
        }
    }

    class ItemViewHolder(private val binding: ItemHomeMenuBinding,private val listener: HomeClickListener) : HomeRecyclerViewHolder(binding){

        fun bind(menu: HomeKeys){
            binding.apply {
                val context = root.context
                menuTextView.text = context.getString(menu.keyName)
                root.setOnClickListener {
                    this@ItemViewHolder.listener.onButtonClick(menu)
                }
            }
        }
    }

}