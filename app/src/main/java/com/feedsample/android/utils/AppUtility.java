package com.feedsample.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.feedsample.android.R;

/**
 * Application utility .
 *
 * @author chetan
 */
public class AppUtility {

    public static void showToastMessage(Context context, String message) {
        if (context == null) return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }

        return networkInfo.isConnected();
    }


    public static String getNetworkError(Context context) {
        String errorMessage = "";
        if (context != null) {
            errorMessage = context.getString(R.string.network_error);
        }
        return errorMessage;
    }
}
