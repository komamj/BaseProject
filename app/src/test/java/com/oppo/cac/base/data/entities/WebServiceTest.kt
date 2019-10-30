package com.oppo.cac.base.data.entities

import com.oppo.cac.base.data.source.remote.WebService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class WebServiceTest {
    private lateinit var mockWebServer: MockWebServer

    private lateinit var webService: WebService

    @Before
    fun startService() {
        mockWebServer = MockWebServer()

        webService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WebService::class.java)
    }

    @Test
    fun `should return news list when network connected`() {
        enqueueResponse("api-response/news.json")

        webService.getNews().test()
            .assertValue {
                it.state
                val newsList = it.data
                newsList.size == 2
                newsList[0].title == "王中王！德约一稳定江山，大满贯稳步追赶或成最终赢家"
                newsList[0].date == "2019-07-15 10:50"
                newsList[0].thumbnailUrls.size == 3
                newsList[1].thumbnailUrls.size == 1
            }.dispose()
    }

    private fun enqueueResponse(fileName: String) {
        javaClass.classLoader?.run {
            val inputStream = getResourceAsStream(fileName)
            val buffer = inputStream.source().buffer()
            mockWebServer.enqueue(MockResponse().setBody(buffer.readString(Charsets.UTF_8)))
        }
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}