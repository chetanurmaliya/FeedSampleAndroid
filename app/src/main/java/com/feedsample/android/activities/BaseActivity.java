package com.feedsample.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.feedsample.android.R;

/**
 * Base of all the activities.
 *
 * @author chetan
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected FragmentManager mFragmentManager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        try {
            mFragmentManager = getSupportFragmentManager();
            actionBar = getSupportActionBar();
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }

    protected void setActionBarTitle(String title) {
        try {
            if (actionBar != null) {
                actionBar.setTitle(title);
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }

    protected void addFragment(Fragment fragment, boolean isAddToBackStack, String fragmentTag) {
        try {
            if (isFinishing() || fragment == null) return;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            if (TextUtils.isEmpty(fragmentTag)) {
                fragmentTransaction.add(R.id.main_container_layout, fragment);
            } else {
                fragmentTransaction.add(R.id.main_container_layout, fragment, fragmentTag);
            }
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(fragmentTag);
            }

            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }
}
