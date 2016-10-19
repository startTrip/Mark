package com.mihua.market;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/8/31
 */
public class MyApplication extends Application {

    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
}
