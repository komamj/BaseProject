package com.oppo.cac.base.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oppo.cac.base.R
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.databinding.ItemNewsBinding

class NewsListAdapter: ListAdapter<News, NewsListAdapter.NewsVH>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        return NewsVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.binding.news = getItem(position)
        holder.binding.executePendingBindings()
    }

    class NewsVH(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title.equals(newItem.title)
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.equals(newItem)
        }
    }
}