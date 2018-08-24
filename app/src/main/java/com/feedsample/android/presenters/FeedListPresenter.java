package com.feedsample.android.presenters;

import com.feedsample.android.entities.FeedList;
import com.feedsample.android.internal.services.FeedServiceImpl;
import com.feedsample.android.mvpviews.FeedListView;
import com.feedsample.android.services.FeedService;
import com.feedsample.android.utils.AppUtility;

/**
 * Presenter for feed list.
 *
 * @author chetan
 */
public class FeedListPresenter extends BasePresenter implements FeedServiceImpl.Callback {

    private FeedListView feedListView;
    private FeedService feedService;

    public FeedListPresenter(FeedListView feedListView) {
        this.feedListView = feedListView;
        feedService = new FeedServiceImpl(this);
    }

    @Override
    public void start() {
        // DO initialization

    }

    @Override
    public void stop() {
        // Call for stop all api calls
        if (feedListView != null) {
            feedListView.hideProgress();
        }
        if (feedService != null) {
            feedService.cancelRequest();
        }
    }

    public void getFeedList(String queryText) {
        if (feedListView == null) {
            return;
        }
        if (!AppUtility.isNetworkAvailable(feedListView.getContext())) {
            feedListView.onErrorResponse(AppUtility.getNetworkError(feedListView.getContext()));
            return;
        }

        if (feedService != null) {
            feedListView.showProgress();
            feedService.getFeedList(queryText);
        }
    }

    public void loadPreviousFeedList(String queryText) {
        if (feedListView == null) {
            return;
        }
        if (!AppUtility.isNetworkAvailable(feedListView.getContext())) {
            feedListView.onErrorResponse(AppUtility.getNetworkError(feedListView.getContext()));
            return;
        }

        if (feedService != null) {
            feedService.getFeedList(queryText);
        }
    }

    @Override
    public void onLoadFeedListSuccess(FeedList feedList) {
        if (feedListView != null) {
            feedListView.hideProgress();
            feedListView.onLoadFeedListSuccess(feedList);
        }
    }

    @Override
    public void onLoadPreviousFeedListSuccess(FeedList feedList) {
        if (feedListView != null) {
            feedListView.onLoadPreviousFeedListSuccess(feedList);
        }
    }

    @Override
    public void onError(String error) {
        if (feedListView != null) {
            feedListView.hideProgress();
            feedListView.onErrorResponse(error);
        }
    }
}
