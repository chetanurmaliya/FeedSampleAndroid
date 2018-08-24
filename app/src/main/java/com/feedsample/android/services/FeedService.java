package com.feedsample.android.services;

/**
 * Feed service.
 *
 * @author chetan
 */
public interface FeedService {

    /**
     * Get feed list.
     *
     * @param queryText
     */
    void getFeedList(String queryText);

    /**
     * Get privious feed list.
     *
     * @param queryText
     */
    void getPreviousFeedList(String queryText);

    /**
     * Cancel request.
     */
    void cancelRequest();
}
