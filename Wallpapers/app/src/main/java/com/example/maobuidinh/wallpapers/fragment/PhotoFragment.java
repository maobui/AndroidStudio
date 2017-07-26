package com.example.maobuidinh.wallpapers.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maobuidinh.wallpapers.R;
import com.example.maobuidinh.wallpapers.adataper.PhotoAdataper;
import com.example.maobuidinh.wallpapers.app.AppConstant;
import com.example.maobuidinh.wallpapers.app.AppController;
import com.example.maobuidinh.wallpapers.helper.GridSpacingItemDecoration;
import com.example.maobuidinh.wallpapers.picasa.model.Wallpaper;
import com.example.maobuidinh.wallpapers.util.PrefManager;
import com.example.maobuidinh.wallpapers.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.maobuidinh.wallpapers.R.id.pbLoader;

/**
 * Created by mao.buidinh on 7/25/2017.
 */

public class PhotoFragment extends Fragment {
    private static final String TAG = PhotoFragment.class.getSimpleName();
    private static final int SPANCOUNT = 2;
    private static final int SPACING = 10;

    private String albumId;
    private static final String bundleAlbumId = "albumId";
    private String mSelectAlbumId;
    private Utils mUtils;
    private RecyclerView mRecyclerView;
    private PhotoAdataper mPhotoAdataper;
    private int mColumnWidth;
    private List<Wallpaper> mWallpaperList;
    private ProgressBar mProgressBar;
    private PrefManager mPrefManager;

    // Picasa JSON response node keys
    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_MEDIA_GROUP = "media$group",
            TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
            TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height", TAG_ID = "id",
            TAG_T = "$t";

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(String albumId) {

        Bundle args = new Bundle();
        args.putString(bundleAlbumId, albumId);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mWallpaperList = new ArrayList<Wallpaper>();
        mPrefManager = new PrefManager(getActivity());

        if( getArguments().getString(bundleAlbumId) != null){
            mSelectAlbumId = getArguments().getString(bundleAlbumId);
            Log.d(TAG, "Selected album id: " + mSelectAlbumId);
        } else {
            Log.d(TAG, "Selected recentlyed added album");
            mSelectAlbumId = null;
        }

        View rootView = inflater.inflate(R.layout.content_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar = (ProgressBar) rootView.findViewById(pbLoader);
        mProgressBar.setVisibility(View.VISIBLE);


        mPhotoAdataper = new PhotoAdataper(getActivity(),mWallpaperList,mColumnWidth);
        setLayoutManager(true);
        mRecyclerView.setAdapter(mPhotoAdataper);
//        mRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // On selecting the grid image, we launch fullscreen activity
//                Intent i = new Intent(getActivity(), FullScreenViewActivity.class);
//
//                // Passing selected image to fullscreen activity
//                Wallpaper photo = mWallpaperList.get(position);
//
//                i.putExtra(FullScreenViewActivity.TAG_SEL_IMAGE, photo);
//                startActivity(i);
//            }
//        });

        mUtils = new Utils(getActivity());
        fetchPhotos();

        return rootView;
    }

    private void setLayoutManager (boolean is)
    {
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView.setHasFixedSize(true);
        if (is) {
            mLayoutManager = new StaggeredGridLayoutManager(SPANCOUNT + 1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            mLayoutManager = new GridLayoutManager(getActivity(), SPANCOUNT);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(SPANCOUNT, mUtils.dpToPx(SPACING), true));
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * Making volley's json object request to fetch list of photos of an
     * album
     * */
    private void fetchPhotos() {
        String url = null;
        if (mSelectAlbumId == null) {
            // Recently Added album url
            url = AppConstant.URL_RECENTLY_ADDED.replace("_PICASA_USER_", mPrefManager.getGoogleUserName());
        } else {
            // Others album url
            url = AppConstant.URL_RECENTLY_ADDED.replace("_PICASA_USER_", mPrefManager.getGoogleUserName()).replace("_ALBUM_ID_", mSelectAlbumId);
        }

        Log.d(TAG, "Final request url: " + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,
                        "List of photos json reponse: "
                                + response.toString());
                try {
                    // Parsing the json response
                    JSONArray entry = response.getJSONObject(TAG_FEED)
                            .getJSONArray(TAG_ENTRY);

                    // looping through each photo and adding it to list
                    // data set
                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject photoObj = (JSONObject) entry.get(i);
                        JSONArray mediacontentArry = photoObj.getJSONObject(TAG_MEDIA_GROUP).getJSONArray(TAG_MEDIA_CONTENT);

                        if (mediacontentArry.length() > 0) {
                            JSONObject mediaObj = (JSONObject) mediacontentArry.get(0);

                            String url = mediaObj.getString(TAG_IMG_URL);

                            String photoJson = photoObj.getJSONObject(TAG_ID).getString(TAG_T) + "&imgmax=d";

                            int width = mediaObj.getInt(TAG_IMG_WIDTH);
                            int height = mediaObj
                                    .getInt(TAG_IMG_HEIGHT);

                            Wallpaper p = new Wallpaper(photoJson, url, width,  height);

                            // Adding the photo to list data set
                            mWallpaperList.add(p);

                            Log.d(TAG, "Photo: " + url + ", w: " + width + ", h: " + height);
                        }
                    }

                    // Notify list adapter about dataset changes. So
                    // that it renders grid again
                    mPhotoAdataper.notifyDataSetChanged();

                    // Hide the loader, make grid visible
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.msg_unknown_error),
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
                Toast.makeText(getActivity(), getString(R.string.msg_wall_fetch_error),
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
}
