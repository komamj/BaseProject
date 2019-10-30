package com.oppo.cac.base.data.source

import com.oppo.cac.base.data.entities.News
import io.reactivex.Observable

open class NewsRepository {
   open fun getNews(): Observable<List<News>> {
        return Observable.just(mutableListOf())
    }

}
