package com.feedsample.android.mvpviews;

import com.feedsample.android.entities.FeedList;

/**
 * View to display and handle feed list response.
 *
 * @author chetan
 */
public interface FeedListView extends BaseMVPView {

    /**
     * Show progress dialog.
     */
    void showProgress();

    /**
     * Hide progress dialog.
     */
    void hideProgress();

    /**
     * Load feed list success response.
     *
     * @param feedList
     */
    void onLoadFeedListSuccess(FeedList feedList);

    /**
     * Load previous feed list success response.
     *
     * @param feedList
     */
    void onLoadPreviousFeedListSuccess(FeedList feedList);

    /**
     * Error response.
     *
     * @param error Error message.
     */
    void onErrorResponse(String error);
}
