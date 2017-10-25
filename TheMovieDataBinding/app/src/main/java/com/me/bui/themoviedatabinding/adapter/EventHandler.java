package com.me.bui.themoviedatabinding.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.me.bui.themoviedatabinding.MovieDetailActivity;
import com.me.bui.themoviedatabinding.model.Result;

/**
 * Created by mao.buidinh on 10/21/2017.
 */

public class EventHandler {
    public Context mContext;

    public EventHandler(Context context) {
        this.mContext = context;
    }

    public void onEventClicked(/*View view,*/ Result result) {
//        Context mContext = view.getContext();
        if (mContext != null) {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("movie", result);
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation((Activity) mContext, view, "aaaaaaaaa");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                Log.e("1","haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                mContext.startActivity(intent, options.toBundle());
//            } else
            {
                Log.e("1","heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                mContext.startActivity(intent);
            }
        }
    }
}
