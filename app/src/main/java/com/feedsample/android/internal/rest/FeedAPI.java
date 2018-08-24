package com.feedsample.android.internal.rest;

import com.feedsample.android.entities.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Feed api.
 *
 * @author chetan
 */
public interface FeedAPI {

    @GET("/services/rest/?method=flickr.photos.search&api_key=641c87bd78e54920b01e9a5d8bb726d7&format=json&nojsoncallback=1&extras=url_q")
    Call<FeedResponse> getFeedList(@Query("text") String queryText);


    @GET("/services/rest/?method=flickr.photos.search&api_key=641c87bd78e54920b01e9a5d8bb726d7&format=json&nojsoncallback=1&extras=url_q")
    Call<FeedResponse> getPreviousFeedList(@Query("text") String queryText);
}
