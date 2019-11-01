package com.oppo.cac.base

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.news.NewsListAdapter.NewsVH
import com.oppo.cac.base.utils.RecyclerViewMatcher
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

//    lateinit var activityScenario: ActivityScenario<MainActivity>
//
//    lateinit var newsRepository: NewsRepository
//
//    @Before
//    fun setUp() {
//        newsRepository = mock(NewsRepository::class.java)
//        `when`(newsRepository.getNews()).thenReturn(Observable.just(listOf(
//
//            News(
//                "甄嬛传：莞嫔手撕颂芝，开头就充满火药味，颂芝的表现该打满分！",
//                "2019-10-30 08:10"
//            ),
//            News(
//                "李姐笑话：结婚这么多年了，你个子多高我还不知道",
//                "2019-10-30 08:00"
//            ),
//            News(
//                "小县金寨： 两年内为何两次解放，两次易名",
//                "2019-10-29 08:10"
//            ),
//            News(
//                "王中王！德约一稳定江山，大满贯稳步追赶或成最终赢家",
//                "2019-10-31 08:10"
//            ),
//            News(
//                "你的南红戒指佩戴正确了吗？不会戴？戳这里找答案！",
//                "2019-10-31 08:00"
//            )
//        )))
//
//        val newsRepositoryProvider = mock(NewsRepositoryProvider::class.java)
//        `when`(newsRepositoryProvider.providerRepository()).thenReturn(newsRepository)
//
//        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback{ activity, stage ->
//            if (stage == Stage.PRE_ON_CREATE && activity is MainActivity) {
//                activity.setNewsRepositoryProvider(newsRepositoryProvider)
//            }
//        }
//
//        activityScenario = ActivityScenario.launch(MainActivity::class.java)
//    }
//
//    @Test
//    fun should_show_news_list_when_launch_app() {
//        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
//        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(0)).check(matches(isDisplayed()))
//        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(0)).check(matches(hasDescendant(
//            withText("甄嬛传：莞嫔手撕颂芝，开头就充满火药味，颂芝的表现该打满分！"))))
//    }
//
//    @Test
//    fun should_show_last_news_item_when_scrolls_to_the_end() {
//        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_news_list)).perform(scrollToPosition<NewsVH>(4))
//        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(4)).check(matches(isDisplayed()))
//        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(4)).check(matches(hasDescendant(
//            withText("你的南红戒指佩戴正确了吗？不会戴？戳这里找答案！"))))
//    }
}