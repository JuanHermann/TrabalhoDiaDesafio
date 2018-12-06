package com.example.juan.trabalhodiadesafio;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {

    private static SingletonRequestQueue rqInstance;

    private Context context;

    private RequestQueue requestQueue;

    private SingletonRequestQueue(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (rqInstance == null) {
            rqInstance = new SingletonRequestQueue(context);
        }

        return rqInstance;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }

}
