package com.rrg.sicpa_test.ui.popular

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rrg.sicpa_test.models.primary.Results
import com.rrg.sicpa_test.databinding.ItemArticleBinding
import com.rrg.sicpa_test.utils.DateTimeFormatter

class PopularArticlesAdapter(private val dateTimeFormatter: DateTimeFormatter): RecyclerView.Adapter<PopularArticlesAdapter.ArticleViewHolder>() {

    var articleList: List<Results> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Results){
            binding.apply {
                titleTextView.text = article.title
                dateTextView.text = dateTimeFormatter.getFormattedDate(article.publishedDate)
            }
        }
    }
}