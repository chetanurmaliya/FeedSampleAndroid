package com.feedsample.android.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Feed response object.
 *
 * @author chetan
 */
public class FeedResponse implements IDataModel {

    @SerializedName("photos")
    private FeedList feedList;

    public FeedList getFeedList() {
        return feedList;
    }
}
