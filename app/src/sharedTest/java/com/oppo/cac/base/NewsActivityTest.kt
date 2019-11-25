package com.oppo.cac.base

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleCallback
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.utils.RecyclerViewMatcher
import io.reactivex.Observable.just
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.LooperMode.Mode.PAUSED

@RunWith(AndroidJUnit4::class)
@LooperMode(PAUSED)
class NewsActivityTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var activityScenario: ActivityScenario<NewsActivity>
    private val newsRepository: NewsRepository = mock(NewsRepository::class.java)

    private val provider = mock(NewsRepositoryProvider::class.java)

    // See {@link androidx.test.internal.runner.lifecycle.ActivityLifecycleMonitorImpl.callbacks}
    // DON'T use lambda or anonymous function or anonymous instance here, otherwise the callback object would got GCed if the test runs slowly
    // Use an object value to hold it
    private val callback = ActivityLifecycleCallback { activity, stage ->
        if (!(stage != Stage.PRE_ON_CREATE || activity !is NewsActivity)) {
            activity.setNewsRepositoryProvider(provider)
        }
    }

    @Before
    fun setUp() {
        `when`(newsRepository.getNews()).thenReturn(
            just(
                listOf(
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
            )
        )
        `when`(provider.provideNewsRepository()).thenReturn(newsRepository)

        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(callback)
        activityScenario = launch(NewsActivity::class.java)
    }

    @Test
    fun should_show_recycler_view_if_app_launches() {
        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_news_list_end_if_scrolls_to_end() {
        onView(withId(R.id.rv_news_list)).perform(scrollToPosition<NewsListAdapter.NewsVH>(4))

        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(4)).check(
            matches(
                allOf(
                    hasDescendant(
                        withText("你的南红戒指佩戴正确了吗？不会戴？戳这里找答案！")
                    ),
                    hasDescendant(withText("2019-10-31 08:00"))
                )
            )
        )
    }

    @Test
    fun should_show_news_list_if_app_launches() {
        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(0)).check(
            matches(
                allOf(
                    hasDescendant(
                        withText("甄嬛传：莞嫔手撕颂芝，开头就充满火药味，颂芝的表现该打满分！")
                    ),
                    hasDescendant(withText("2019-10-30 08:10"))
                )
            )
        )
    }

    @After
    fun tearDown() {
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(callback)
    }
}