package com.rrg.sicpa_test.models.keys

import androidx.annotation.LayoutRes
import com.rrg.sicpa_test.R

enum class TypeKeys(@LayoutRes val layoutId: Int) {
    Header(R.layout.item_home_header), HomeMenu(R.layout.item_home_menu)
}