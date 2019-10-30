package com.oppo.cac.base.data.source.remote

import com.oppo.cac.base.data.entities.DataModel
import com.oppo.cac.base.data.entities.News
import io.reactivex.Observable
import retrofit2.http.GET

interface WebService {
    @GET("news/list")
    fun getNews(): Observable<DataModel<List<News>>>

}
