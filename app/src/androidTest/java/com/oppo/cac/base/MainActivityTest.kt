package com.oppo.cac.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.news.NewsViewModel
import com.oppo.cac.base.utils.RecyclerViewMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsViewModel

    private val news = MutableLiveData<List<News>>()

    @Before
    fun setUp() {
        viewModel = mock(NewsViewModel::class.java)
        `when`(viewModel.newsList).thenReturn(news)
    }

    @Test
    fun `should_show_news_list_if_launch_app`() {
        postData()
        onView(recyclerViewMatcher().atPosition(0)).check(matches(isDisplayed()))
        onView(recyclerViewMatcher().atPosition(0)).check(matches(hasDescendant(withText("甄嬛传：莞嫔手撕颂芝，开头就充满火药味，颂芝的表现该打满分！"))))
    }

    @Test
    fun `should_show_last_news_if_scroll_end`() {
        postData()

        onView(withId(R.id.rv_news_list)).perform(scrollToPosition<NewsListAdapter.NewsVH>(4))
        onView(recyclerViewMatcher().atPosition(4)).check(matches(isDisplayed()))
        onView(recyclerViewMatcher().atPosition(4)).check(matches(hasDescendant(withText("你的南红戒指佩戴正确了吗？不会戴？戳这里找答案！"))))
    }

    private fun recyclerViewMatcher() = RecyclerViewMatcher(R.id.rv_news_list)

    private fun postData() {
        activityRule.activity.removeObservers()
        activityRule.activity.observerViewModel(viewModel)
        activityRule.activity.runOnUiThread {
            news.postValue(mockedNews)
        }
    }


    private val mockedNews: List<News> = listOf(

        News(
            "甄嬛传：莞嫔手撕颂芝，开头就充满火药味，颂芝的表现该打满分！",
            "2019-10-30 08:10"
        ),
        News(
            "李姐笑话：结婚这么多年了，你个子多高我还不知道",
            "2019-10-30 08:00"
        ),
        News(
            "小县金寨： 两年内为何两次解放，两次易名",
            "2019-10-29 08:10"
        ),
        News(
            "王中王！德约一稳定江山，大满贯稳步追赶或成最终赢家",
            "2019-10-31 08:10"
        ),
        News(
            "你的南红戒指佩戴正确了吗？不会戴？戳这里找答案！",
            "2019-10-31 08:00"
        )
    )
}