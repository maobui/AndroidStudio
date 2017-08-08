package com.example.maobuidinh.themovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.maobuidinh.themovie.MovieDetailActivity;
import com.example.maobuidinh.themovie.R;
import com.example.maobuidinh.themovie.model.Result;

import static com.example.maobuidinh.themovie.AppConstant.POSTER_URL_IMG;

/**
 * Created by mao.buidinh on 8/8/2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
    private static final String TAG = MovieHolder.class.getSimpleName();

    private TextView mRatting;
    private TextView mMovieTitle;
    private TextView mMovieDesc;
    private TextView mYear; // displays "year | language"
    private ImageView mPosterImg;
    private ProgressBar mProgress;

    private Context mContext;

    public MovieHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        mRatting = (TextView) itemView.findViewById(R.id.rating);
        mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        mMovieDesc = (TextView) itemView.findViewById(R.id.movie_desc);
        mYear = (TextView) itemView.findViewById(R.id.movie_year);
        mPosterImg = (ImageView) itemView.findViewById(R.id.movie_poster);
        mProgress = (ProgressBar) itemView.findViewById(R.id.movie_progress);

        // Listener for all item_list layout.
        //itemView.setOnClickListener(this);

        // Listener for only movie_poster.
        //mPosterImg.setOnClickListener(this);

        //        @Override
//        public void onClick(View v) {
//            Log.e(TAG, "onClick ******************************* "+  (v.getId() == R.id.movie_poster));
//            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
//            intent.putExtra("movie", movieResults.get(0));
//            v.getContext().startActivity(intent);
//        }
    }

    public void bind(final Result result) {
        mPosterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick ******************************* ");
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra("movie", result);
                v.getContext().startActivity(intent);
            }
        });

        mRatting.setText(result.getVoteAverage().toString());
        mMovieTitle.setText(result.getTitle());


        mYear.setText(
                result.getReleaseDate().substring(0, 4)  // we want the year only
                        + " | "
                        + result.getOriginalLanguage().toUpperCase()
        );
        mMovieDesc.setText(result.getOverview());

        Glide
                .with(mContext)
                .load(POSTER_URL_IMG + result.getPosterPath())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // TODO: handle failure
                        mProgress.setVisibility(View.GONE);
                        Log.e(TAG, " loadImage error:  " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // image ready, hide progress now
                        mProgress.setVisibility(View.GONE);
                        return false;   // return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade()
                //.placeholder(R.drawable.poster) // can also be a drawable first until to show real image.
                .error(android.R.drawable.ic_menu_report_image) // will be displayed if the image cannot be loaded
                .into(mPosterImg);
    }

}
