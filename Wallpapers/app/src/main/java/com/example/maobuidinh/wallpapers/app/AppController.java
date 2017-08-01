package com.example.maobuidinh.wallpapers.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.maobuidinh.wallpapers.util.LruBitmapCache;
import com.example.maobuidinh.wallpapers.util.PrefManager;

/**
 * Created by mao.buidinh on 7/25/2017.
 */

public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 1000; // 1s , default 5s.

    private static AppController mInstance;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;
    private PrefManager mPrefManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPrefManager = new PrefManager(this);
    }

    public static synchronized  AppController getInstance() {
        return mInstance;
    }

    public PrefManager getPrefManager(){
        if (mPrefManager == null){
            mPrefManager = new PrefManager(this);
        }
        return mPrefManager;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public LruBitmapCache getLruBitmapCache(){
        if (mLruBitmapCache == null){
            mLruBitmapCache = new LruBitmapCache();
        }
        return  mLruBitmapCache;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if(mImageLoader == null ){
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }
        return mImageLoader;
    }

    public <T> void addToRequestqQueue(Request<T> request, String tag){
        //request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TextUtils.isEmpty(tag)? TAG: tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestqQueue(Request<T> request){
        //request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPedingRequests( Object o){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(o);
        }
    }
}
