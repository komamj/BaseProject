package com.oppo.cac.base.news

import androidx.lifecycle.ViewModel
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

class NewsViewModel constructor(private val repository: NewsRepository) :ViewModel(){
    var errorMessage: String? = ""
    var newsList = mutableListOf<News>()
    var isLoading: Boolean = false
    val disposables=CompositeDisposable()

    fun getNews() {
        isLoading = true
        errorMessage=""
        var dispoable=repository.getNews().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    newsList.clear()
                    newsList.addAll(it)
                }, {
                    isLoading = false
                    errorMessage=it.message
                }, {
                    isLoading = false
                }
            )
        disposables.add(dispoable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
