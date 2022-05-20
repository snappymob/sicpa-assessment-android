package com.rrg.sicpa_test.models.keys

import androidx.annotation.StringRes
import com.rrg.sicpa_test.R

enum class HomeKeys(@StringRes val keyName: Int, val keyTypeKeys: TypeKeys) {
    SearchHeader(R.string.search, TypeKeys.Header),
    SearchArticles(R.string.search_articles, TypeKeys.HomeMenu),
    PopularHeader(R.string.popular, TypeKeys.Header),
    MostViewed(R.string.most_viewed, TypeKeys.HomeMenu),
    MostShared(R.string.most_shared, TypeKeys.HomeMenu),
    MostEmailed(R.string.most_emailed, TypeKeys.HomeMenu)
}