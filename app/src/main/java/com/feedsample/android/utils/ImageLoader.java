package com.feedsample.android.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Image loader utility.
 *
 * @author chetan.
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    /**
     * Loads image into image view.
     *
     * @param imageURL  Image url to load.
     * @param imageView Image view to load the image.
     */
    public static void load(String imageURL, final ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(imageURL)
                    .into(imageView);
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }
}
