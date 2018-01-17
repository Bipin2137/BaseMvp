package com.dexter.basemvpwithoutdagger.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.HttpException;

/**
 * Created by Admin on 06-12-2017.
 */

public class NetworkUtils {

    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    private static final String NETWORK_ERROR_MESSAGE = "No Internet Connection!";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getStringError(Throwable error) {
        error.printStackTrace();
        if (error instanceof IOException) return NETWORK_ERROR_MESSAGE;
        else if (!(error instanceof HttpException)) {
            if (error instanceof NullPointerException) {
                return error.getLocalizedMessage();
            } else {
                return DEFAULT_ERROR_MESSAGE;
            }
        } else {
            retrofit2.Response<?> response = ((HttpException) error).response();
            if (response != null) {
                String status = getJsonStringFromResponse(response);
                if (!android.text.TextUtils.isEmpty(status)) return status;

                Map<String, List<String>> headers = response.headers().toMultimap();
                if (headers.containsKey(ERROR_MESSAGE_HEADER))
                    return headers.get(ERROR_MESSAGE_HEADER).get(0);
            }
        }
        return "";
    }

    private static String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            return response.errorBody().string();
        } catch (Exception e) {
            return null;
        }
    }
}
