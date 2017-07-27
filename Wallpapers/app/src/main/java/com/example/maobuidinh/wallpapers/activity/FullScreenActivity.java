package com.example.maobuidinh.wallpapers.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maobuidinh.wallpapers.R;
import com.example.maobuidinh.wallpapers.app.AppController;
import com.example.maobuidinh.wallpapers.picasa.model.Wallpaper;
import com.example.maobuidinh.wallpapers.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.maobuidinh.wallpapers.R.id.llDownloadWallpaper;
import static com.example.maobuidinh.wallpapers.R.id.llSetWallpaper;
import static com.example.maobuidinh.wallpapers.R.id.pbLoader;

public class FullScreenActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final String TAG = FullScreenActivity.class.getSimpleName();

    private Utils mUtils;

    public static final String TAG_SEL_IMAGE = "selectedImage";
    private Wallpaper mWallpaper;
    private ImageView m_imgFullScreen;
    private LinearLayout m_llSetWallpaper, m_llDownloadWallpaper;
    private ProgressBar m_pbLoader;

    // Picasa JSON response node keys
    private static final String TAG_ENTRY = "entry",
            TAG_MEDIA_GROUP = "media$group",
            TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
            TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        m_imgFullScreen = (ImageView) findViewById(R.id.imgFullScreen);
        m_llSetWallpaper = (LinearLayout) findViewById(llSetWallpaper);
        m_llDownloadWallpaper = (LinearLayout) findViewById(llDownloadWallpaper);
        m_pbLoader = (ProgressBar) findViewById(pbLoader);

        // hide the action bar in fullscreen mode
        getActionBar().hide();

        mUtils = new Utils(getApplicationContext());

        // layout click listeners
        m_llSetWallpaper.setOnClickListener(this);
        m_llDownloadWallpaper.setOnClickListener(this);

        // setting layout buttons alpha/opacity
        m_llSetWallpaper.getBackground().setAlpha(70);
        m_llDownloadWallpaper.getBackground().setAlpha(70);

        Intent i = getIntent();
        mWallpaper = (Wallpaper) i.getSerializableExtra(TAG_SEL_IMAGE);

        // check for selected photo null
        if (mWallpaper != null) {

            // fetch photo full resolution image by making another json request
            fetchPhoto();

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_unknown_error)
                    , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = ((BitmapDrawable)m_imgFullScreen.getDrawable()).getBitmap();
        switch (v.getId()){
            case llSetWallpaper:
                mUtils.setAsWallpaper(bitmap);
                break;
            case llDownloadWallpaper:
                mUtils.saveImageToSDCard(bitmap);
                break;
            default:
                break;
        }
    }

    private void fetchPhoto() {
        String url = mWallpaper.getPhotoJson();

        // show loader before making request
        m_pbLoader.setVisibility(View.VISIBLE);
        m_llSetWallpaper.setVisibility(View.GONE);
        m_llDownloadWallpaper.setVisibility(View.GONE);

        // volley's json obj request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,
                        "Image full resolution json: "
                                + response.toString());
                try {
                    // Parsing the json response
                    JSONObject entry = response.getJSONObject(TAG_ENTRY);

                    JSONArray mediacontentArry = entry.getJSONObject(TAG_MEDIA_GROUP).getJSONArray(TAG_MEDIA_CONTENT);

                    JSONObject mediaObj = (JSONObject) mediacontentArry.get(0);

                    String fullResolutionUrl = mediaObj
                            .getString(TAG_IMG_URL);

                    // image full resolution widht and height
                    final int width = mediaObj.getInt(TAG_IMG_WIDTH);
                    final int height = mediaObj.getInt(TAG_IMG_HEIGHT);

                    Log.d(TAG, "Full resolution image. url: " + fullResolutionUrl + ", w: " + width + ", h: " + height);

                    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                    // We download image into ImageView instead of
                    // NetworkImageView to have callback methods
                    // Currently NetworkImageView doesn't have callback
                    // methods

                    imageLoader.get(fullResolutionUrl,
                            new ImageLoader.ImageListener() {

                                @Override
                                public void onErrorResponse(
                                        VolleyError arg0) {
                                    Toast.makeText( getApplicationContext(), getString(R.string.msg_wall_fetch_error),
                                            Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(
                                        ImageLoader.ImageContainer response,
                                        boolean arg1) {
                                    if (response.getBitmap() != null) {
                                        // load bitmap into imageview
                                        m_imgFullScreen.setImageBitmap(response.getBitmap());
                                        adjustImageAspect(width, height);

                                        // hide loader and show set &
                                        // download buttons
                                        m_pbLoader.setVisibility(View.GONE);
                                        m_llSetWallpaper.setVisibility(View.VISIBLE);
                                        m_llDownloadWallpaper.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_unknown_error),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                // unable to fetch wallpapers
                // either google username is wrong or
                // devices doesn't have internet connection
                Toast.makeText(getApplicationContext(), getString(R.string.msg_wall_fetch_error),
                        Toast.LENGTH_LONG).show();

            }
        });

        // Remove the url from cache
        AppController.getInstance().getRequestQueue().getCache().remove(url);

        // Disable the cache for this url, so that it always fetches updated
        // json
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        AppController.getInstance().addToRequestqQueue(jsonObjReq);
    }

    private void adjustImageAspect(int width, int height) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if (width == 0 || height == 0)
            return;

        int sHeight = 0;

        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            sHeight = size.y;
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            sHeight = display.getHeight();
        }

        int new_width = (int) Math.floor((double) width * (double) sHeight / (double) height);
        params.width = new_width;
        params.height = sHeight;

        Log.d(TAG, "Fullscreen image new dimensions: w = " + new_width + ", h = " + sHeight);

        m_imgFullScreen.setLayoutParams(params);
    }

}
