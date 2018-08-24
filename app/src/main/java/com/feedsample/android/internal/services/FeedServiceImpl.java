package com.feedsample.android.internal.services;

import android.support.annotation.NonNull;

import com.feedsample.android.entities.FeedList;
import com.feedsample.android.entities.FeedResponse;
import com.feedsample.android.internal.rest.FeedAPI;
import com.feedsample.android.services.FeedService;
import com.feedsample.android.utils.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Feed service implementation.
 *
 * @author chetan
 */
public class FeedServiceImpl implements FeedService {

    private FeedAPI feedAPI;
    private Callback callback;
    private Call<FeedResponse> feedAPICall;

    public FeedServiceImpl(Callback callback) {
        this.callback = callback;
        feedAPI = RetrofitClientInstance.getRetrofitInstance().create(FeedAPI.class);
    }

    @Override
    public void getFeedList(String queryText) {
        feedAPICall = feedAPI.getFeedList(queryText);
        feedAPICall.enqueue(new retrofit2.Callback<FeedResponse>() {

            @Override
            public void onResponse(@NonNull Call<FeedResponse> call, @NonNull Response<FeedResponse> response) {
                if (callback != null) {
                    if (response != null && response.body() != null) {
                        callback.onLoadFeedListSuccess(response.body().getFeedList());
                    } else {
                        callback.onError("Server error");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedResponse> call, @NonNull Throwable t) {
                setupFailureResponse(call);
            }
        });
    }

    @Override
    public void getPreviousFeedList(String queryText) {
        feedAPICall = feedAPI.getPreviousFeedList(queryText);
        feedAPICall.enqueue(new retrofit2.Callback<FeedResponse>() {

            @Override
            public void onResponse(@NonNull Call<FeedResponse> call, @NonNull Response<FeedResponse> response) {
                if (callback != null) {
                    if (response != null && response.body() != null) {
                        callback.onLoadPreviousFeedListSuccess(response.body().getFeedList());
                    } else {
                        callback.onError("Server error");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedResponse> call, @NonNull Throwable t) {
                setupFailureResponse(call);
            }
        });
    }

    private void setupFailureResponse(Call<FeedResponse> call) {
        if (callback != null) {
            if (!call.isCanceled()) {
                callback.onError("Server error");
            }
        }
    }

    @Override
    public void cancelRequest() {
        if (feedAPICall != null) {
            feedAPICall.cancel();
        }
    }

    /**
     * Callback for feed response.
     *
     * @author chetan
     */
    public interface Callback {
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
        void onError(String error);
    }
}
