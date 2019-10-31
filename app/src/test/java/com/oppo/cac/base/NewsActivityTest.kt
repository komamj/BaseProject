package com.oppo.cac.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.oppo.cac.base.data.entities.News
import com.oppo.cac.base.data.source.NewsRepository
import com.oppo.cac.base.news.NewsListAdapter
import com.oppo.cac.base.utils.RecyclerViewMatcher
import com.oppo.cac.base.util.RxJavaRule
import io.reactivex.Observable.just
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
class JunitNewsActivityTest {

    @get:Rule val rule = InstantTaskExecutorRule()
    @get:Rule val rule1 = RxJavaRule()


    private lateinit var activityScenario:ActivityScenario<NewsActivity>
    private lateinit var newsRepository : NewsRepository
    @Before
    fun setUp() {
        newsRepository = mock(NewsRepository::class.java)
        `when`(newsRepository.getNews()).thenReturn(
            just(
                listOf(
                    News("Test1", "2019-10-31 10:00"),
                    News("Test2", "2019-10-30 10:00"),
                    News("Test3", "2019-10-29 10:00"),
                    News("Test4", "2019-10-28 10:00"),
                    News("Test5", "2019-10-27 10:00")
                )
            )
        )

        val provider = mock(NewsRepositoryProvider::class.java)
        `when`(provider.provideNewsRepository()).thenReturn(newsRepository)

        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback { activity, stage ->
            if (!(stage != Stage.PRE_ON_CREATE || activity !is NewsActivity)) {
                activity.setNewsRepositoryProvider(provider)
            }
        }
        activityScenario = launch(NewsActivity::class.java)
    }

    @Test
    fun should_show_recycler_view_if_app_launches() {
        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_news_list_end_if_scrolls_to_end() {

        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_news_list)).perform(scrollToPosition<NewsListAdapter.NewsVH>(4))

        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(4)).check(matches(hasDescendant(
            withText("Test5"))))

    }

    @Test
    fun should_show_news_list_if_app_launches() {
        // Then
        onView(withId(R.id.rv_news_list)).check(matches(isDisplayed()))
        onView(RecyclerViewMatcher(R.id.rv_news_list).atPosition(0)).check(matches(hasDescendant(
            withText("Test1"))))
    }
}