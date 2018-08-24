package com.feedsample.android.activities;

import android.os.Bundle;
import android.text.TextUtils;

import com.feedsample.android.fragments.FeedListFragment;

/**
 * Feed list activity.
 *
 * @author chetan
 */
public class FeedListActivity extends BaseActivity implements FeedListFragment.Callback {

    private FeedListFragment feedListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFeedListFragment();
    }

    @Override
    protected void onDestroy() {
        feedListFragment = null;
        super.onDestroy();
    }

    private void addFeedListFragment() {
        feedListFragment = (FeedListFragment) mFragmentManager.findFragmentByTag(FeedListFragment.TAG);
        if (feedListFragment == null) {
            feedListFragment = FeedListFragment.newInstance(getIntent());
            addFragment(feedListFragment, false, FeedListFragment.TAG);
        }
    }

    @Override
    public void onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)) {
            setActionBarTitle(query);
        }
    }
}
