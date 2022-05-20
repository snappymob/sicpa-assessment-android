package com.rrg.sicpa_test.ui.article_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rrg.sicpa_test.databinding.ItemArticleBinding
import com.rrg.sicpa_test.models.primary.Article
import com.rrg.sicpa_test.utils.DateTimeFormatter

class ArticleListAdapter(private val dateTimeFormatter: DateTimeFormatter): PagingDataAdapter<Article,ArticleListAdapter.ArticleViewHolder>(ArticleComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.apply {
                titleTextView.text = article.headline?.main ?: "-"
                dateTextView.text = dateTimeFormatter.getFormattedDate(article.pubDate)
            }
        }
    }

    object ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.Id == newItem.Id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}