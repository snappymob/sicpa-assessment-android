package com.rrg.sicpa_test.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rrg.sicpa_test.R
import com.rrg.sicpa_test.databinding.ItemHomeHeaderBinding
import com.rrg.sicpa_test.databinding.ItemHomeMenuBinding
import com.rrg.sicpa_test.models.keys.HomeKeys
import java.lang.IllegalArgumentException

class HomeAdapter(private val listener: HomeClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val homeLayoutKeys: Array<HomeKeys> = HomeKeys.values() // Represents home page layout.

    override fun getItemViewType(position: Int): Int {
        return homeLayoutKeys[position].keyTypeKeys.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_home_header -> {
                HomeRecyclerViewHolder.HeaderViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            R.layout.item_home_menu -> {
                HomeRecyclerViewHolder.ItemViewHolder(
                    ItemHomeMenuBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ), listener
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid OR Undefined View Type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.item_home_header -> {
                (holder as HomeRecyclerViewHolder.HeaderViewHolder).bind(homeLayoutKeys[position].keyName)
            }
            R.layout.item_home_menu -> {
                (holder as HomeRecyclerViewHolder.ItemViewHolder).bind(homeLayoutKeys[position])
            }
            else -> {
                throw IllegalArgumentException("Invalid View Type")
            }
        }
    }

    override fun getItemCount(): Int {
        return homeLayoutKeys.size
    }
}