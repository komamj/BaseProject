package com.oppo.cac.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.databinding.ActivityMainBinding
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.news.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: NewsListAdapter
    private lateinit var viewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        adapter = NewsListAdapter()

        with(binding.rvNewsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainActivity.adapter
        }

        viewModel = NewsViewModel(NewsRepository())
        observerViewModel(viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNews()
    }

    fun observerViewModel(viewModel: NewsViewModel) {
        viewModel.newsList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    fun removeObservers() {
        viewModel.newsList.removeObservers(this)
    }

}
