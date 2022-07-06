package com.example.sicpa_assessment_android.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.example.sicpa_assessment_android.R
import com.example.sicpa_assessment_android.databinding.ItemArticleResultBinding
import com.example.sicpa_assessment_android.shared.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_article_result)
abstract class ArticleResultEpoxyModel :
    ViewBindingEpoxyModelWithHolder<ItemArticleResultBinding>() {

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute
    var date: String? = null

    override fun ItemArticleResultBinding.bind() {
        titleTextView.text = title
        dateTextView.text = date
    }
}