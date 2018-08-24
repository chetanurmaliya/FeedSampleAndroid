package com.feedsample.android.mvpviews;

import android.content.Context;

/**
 * Super type for all MVP views.
 *
 * @author chetan
 */
public interface BaseMVPView {
    /**
     * Returns the application context.
     *
     * @return Application context.
     */
    Context getContext();
}