package com.oppo.cac.base.news

import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.util.RxJavaRule
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class NewsViewModelTest {
    private lateinit var viewModel: NewsViewModel

    private val repository = mock(NewsRepository::class.java)

    @Rule
    @JvmField
    var rxJava: RxJavaRule = RxJavaRule()

    @Before
    fun setUp() {
        viewModel = NewsViewModel(repository)
    }

    private val testScheduler=TestScheduler()

    @Test
    fun `should return true when getNews is loading`() {
        Assert.assertFalse(viewModel.isLoading)
        `when`(repository.getNews()).thenReturn(Observable.just(emptyList<News>())
            .observeOn(testScheduler))
        viewModel.getNews()
        Assert.assertTrue(viewModel.isLoading)
        verify(repository).getNews()
        testScheduler.triggerActions()
        Assert.assertFalse(viewModel.isLoading)
    }

    @Test
    fun `should return news list when getNews successful`() {
        val news1 = News("title1", "2017")
        val news2 = News("title2", "2018")
        val news3 = News("title3", "2019")
        val newsList = mutableListOf<News>().apply {
            add(news1)
            add(news2)
            add(news3)
        }
        `when`(repository.getNews()).thenReturn(Observable.just(newsList))
        viewModel.getNews()
        verify(repository).getNews()
        Assert.assertEquals(viewModel.newsList.value?.size, 3)
        Assert.assertEquals(news1, viewModel.newsList.value?.get(0))
        Assert.assertEquals(news2,viewModel.newsList.value?.get(1))
        Assert.assertEquals(news3,viewModel.newsList.value?.get(2))

    }

    @Test
    fun `should return request failed when getNews server failed`() {
        Assert.assertTrue(viewModel.errorMessage.isNullOrEmpty())
        `when`(repository.getNews()).thenReturn(Observable.error<List<News>> {
            Throwable("request failed")
        }.observeOn(testScheduler))
        viewModel.getNews()
        Assert.assertTrue(viewModel.errorMessage.isNullOrEmpty())
        testScheduler.triggerActions()
        Assert.assertEquals(viewModel.errorMessage, "request failed")

    }

}