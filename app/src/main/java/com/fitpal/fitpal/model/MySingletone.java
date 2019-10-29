package com.fitpal.fitpal.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletone {

    private static MySingletone mInstance;
    private RequestQueue mRequestQueue;

    private MySingletone(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MySingletone getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingletone(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
