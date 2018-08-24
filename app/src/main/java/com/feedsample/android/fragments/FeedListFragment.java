package com.feedsample.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.feedsample.android.R;
import com.feedsample.android.adapter.FeedListAdapter;
import com.feedsample.android.entities.Feed;
import com.feedsample.android.entities.FeedList;
import com.feedsample.android.mvpviews.FeedListView;
import com.feedsample.android.presenters.FeedListPresenter;
import com.feedsample.android.utils.AppUtility;

import java.util.List;

/**
 * Fragment for feed list.
 *
 * @author chetan
 */
public class FeedListFragment extends Fragment implements FeedListView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "FeedListFragment";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Callback callback;
    private FeedListPresenter feedListPresenter;
    private String queryText;
    private List<Feed> feedListData;
    private FeedListAdapter feedListAdapter;

    public static FeedListFragment newInstance(Intent intent) {
        FeedListFragment feedListFragment = new FeedListFragment();
        if (intent != null && intent.getExtras() != null) {
            feedListFragment.setArguments(intent.getExtras());
        }
        return feedListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.feed_list);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        feedListPresenter = new FeedListPresenter(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.action_search);
            if (menuItem != null) {
                setupSearchView((SearchView) menuItem.getActionView());
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroy() {
        if (feedListPresenter != null) {
            feedListPresenter.stop();
            feedListPresenter = null;
        }
        super.onDestroy();
    }

    private void setupSearchView(final SearchView searchView) {
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    queryText = query;
                    if (feedListPresenter != null && !TextUtils.isEmpty(query)) {
                        feedListPresenter.getFeedList(query);
                    }

                    if (callback != null) {
                        callback.onQueryTextSubmit(query);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        setSwipeRefreshLayoutState(true);
        if (feedListPresenter != null) {
            feedListPresenter.loadPreviousFeedList(queryText);
        }
    }

    private void setSwipeRefreshLayoutState(boolean isRefreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(isRefreshing);
        }
    }

    @Override
    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadFeedListSuccess(FeedList feedList) {
        setSwipeRefreshLayoutState(false);
        if (feedList != null && feedList.getFeedList() != null) {
            feedListData = feedList.getFeedList();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            feedListAdapter = new FeedListAdapter(feedListData);
            recyclerView.setAdapter(feedListAdapter);
        }
    }

    @Override
    public void onLoadPreviousFeedListSuccess(FeedList feedList) {
        setSwipeRefreshLayoutState(false);
        if (feedList != null && feedList.getFeedList() != null) {
            if (feedListData != null && !feedListData.isEmpty()) {
                feedListData.addAll(0, feedList.getFeedList());
                if (feedListAdapter != null) {
                    feedListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(String errorMessage) {
        setSwipeRefreshLayoutState(false);
        if (!TextUtils.isEmpty(errorMessage)) {
            AppUtility.showToastMessage(getContext(), errorMessage);
        }
    }

    public interface Callback {
        void onQueryTextSubmit(String query);
    }
}
