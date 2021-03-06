package com.feedsample.android.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Feed object.
 *
 * @author chetan
 */
public class Feed implements IDataModel {

    private String id;
    private String title;

    @SerializedName("url_q")
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
