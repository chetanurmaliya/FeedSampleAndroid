package com.feedsample.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import com.feedsample.android.mvpviews.FeedListView;
import com.feedsample.android.presenters.FeedListPresenter;
import com.feedsample.android.utils.AppUtility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AppUtility.class, TextUtils.class, ConnectivityManager.class})

public class FeedListUnitTest {

    @Mock
    private Context context;

    @Mock
    private FeedListPresenter feedListPresenter;

    @Mock
    private FeedListView feedListView;

    /**
     * Method to setup all static classes.
     */
    @Before
    public void setUp() {
        //for single instance/ static class
        MockitoAnnotations.initMocks(this);
        mockStatic(AppUtility.class);
        mockStatic(Log.class);
        mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                return !(a != null && a.length() > 0);
            }
        });
        feedListView = mock(FeedListView.class);
        when(feedListView.getContext()).thenReturn(context);

        feedListPresenter = new FeedListPresenter(feedListView);
    }

    @Test
    public void loadFeedListTest() {
        PowerMockito.when(!AppUtility.isNetworkAvailable(context)).thenReturn(true);
        feedListPresenter.getFeedList("shirt");
        verify(feedListView, times(1)).showProgress();
    }

    @Test
    public void stopTest() {
        feedListPresenter.stop();
        verify(feedListView, times(1)).hideProgress();
    }
}