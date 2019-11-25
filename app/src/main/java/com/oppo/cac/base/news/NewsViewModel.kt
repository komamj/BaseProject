package com.oppo.cac.base.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oppo.cac.base.annotation.OpenForTesting
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@OpenForTesting
class NewsViewModel constructor(private val repository: NewsRepository) : ViewModel() {
    var errorMessage: String? = ""
    var newsList = MutableLiveData<List<News>>()
    var isLoading: Boolean = false
    val disposables = CompositeDisposable()

    fun getNews() {
        isLoading = true
        errorMessage = ""
        val disposable = repository.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    newsList.postValue(it)
                }, {
                    isLoading = false
                    errorMessage = it.message
                }, {
                    isLoading = false
                }
            )
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
