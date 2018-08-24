package com.feedsample.android.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Feed list object.
 *
 * @author chetan
 */
public class FeedList implements IDataModel {

    @SerializedName("photo")
    private List<Feed> feedList;

    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }
}
