package com.feedsample.android.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Callback listener for endless scroll.
 *
 * @author chetan.
 */
public abstract class OnEndlessScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = "OnEndlessScrollListener";
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean isLoading = true; // True if we are still waiting for the last set of data to load.
    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 4;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int maxVisibleItemCount = 1;
    private int currentPage = 1;
    private LinearLayoutManager linearLayoutManager;
    //To avoid recursive calls
    private boolean autoSet = true;
    private boolean isError;
    private int firstVisibleItemPosition;

    public OnEndlessScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dx + dy == 0) {
            return;
        }

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (maxVisibleItemCount < visibleItemCount) {
            maxVisibleItemCount = visibleItemCount;
        }

        if ((isLoading && (totalItemCount > previousTotal)) || previousTotal > totalItemCount) {
            isLoading = false;
            previousTotal = totalItemCount;
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            currentPage++;
            onLoadMore(currentPage);
            isLoading = true;
        }

        if (isError && (totalItemCount - maxVisibleItemCount) > (firstVisibleItem + visibleThreshold)) {
            isError = false;
            // Enable load more if got error response.
            previousTotal = firstVisibleItem;
        }

        if (firstVisibleItemPosition != firstVisibleItem) {
            firstVisibleItemPosition = firstVisibleItem;
            onFirstItemChanged(firstVisibleItemPosition);
        }
    }

    /**
     * Load more
     */
    public void enableLoadMore() {
        isError = true;
        currentPage--;
    }

    public abstract void onLoadMore(int currentPage);

    // Not needed for all the recycle view scroll listener.
    public void onFirstItemChanged(int firstVisibleItemPosition) {
    }

    public void onScrollStopped() {
    }

    public void onScrollStarted() {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!autoSet) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //Scroll Stopped
                autoSet = true;
                onScrollStopped();
            }
        }
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            if (!autoSet) {
                onScrollStarted();
            }
            autoSet = false;
        }
    }
}