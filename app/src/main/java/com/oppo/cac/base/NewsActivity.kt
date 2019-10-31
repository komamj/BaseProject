package com.oppo.cac.base

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.databinding.ActivityNewsBinding
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.news.NewsViewModel

class NewsActivity : AppCompatActivity() {
    private var provider: NewsRepositoryProvider = NewsRepositoryProvider()
    lateinit var adapter: NewsListAdapter

    private lateinit var newsViewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        newsViewModel = NewsViewModel(provider.provideNewsRepository())
        binding.viewModel = this.newsViewModel
        binding.setLifecycleOwner { lifecycle }
        adapter = NewsListAdapter()
        binding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = this@NewsActivity.adapter
        }
        newsViewModel.newsList.observe(binding.lifecycleOwner!!,
            androidx.lifecycle.Observer<List<News>> {
                adapter.submitList(it)
            })
    }

    override fun onResume() {
        super.onResume()
        newsViewModel.getNews()
    }


    @VisibleForTesting
    fun setNewsRepositoryProvider(provider: NewsRepositoryProvider) {
        this.provider = provider
    }
}

open class NewsRepositoryProvider {
    open fun provideNewsRepository(): NewsRepository {
        return NewsRepository()
    }
}