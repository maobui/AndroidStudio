package com.me.bui.themoviedatabinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.me.bui.themoviedatabinding.model.Result;

import static com.me.bui.themoviedatabinding.AppConstant.BACKDROP_URL_IMG;
import static com.me.bui.themoviedatabinding.AppConstant.POSTER_URL_IMG;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private LinearLayout m_ll_watch_trailer, m_ll_details;
    private ImageView m_img_bg, m_img_poster;
    private TextView m_txt_title, m_txt_year, m_txt_desc;
    private Button m_btn_watch_trailer;
    private ProgressBar mProgressPoster;

    private Result mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();
        mMovie = (Result) getIntent().getSerializableExtra("movie");
        Log.e(TAG, " Result movie title :  " + mMovie.getTitle());

        initView();
    }

    private void initView() {
        m_ll_watch_trailer = (LinearLayout) findViewById(R.id.ll_watch_trailer);
        m_ll_details = (LinearLayout) findViewById(R.id.ll_details);
        m_img_bg = (ImageView) findViewById(R.id.img_bg);
        m_img_poster = (ImageView) findViewById(R.id.img_poster);
        m_txt_title = (TextView) findViewById(R.id.txt_title);
        m_txt_year = (TextView) findViewById(R.id.txt_year);
        m_txt_desc = (TextView) findViewById(R.id.txt_desc);
        mProgressPoster = (ProgressBar) findViewById(R.id.progress_poster);
        m_btn_watch_trailer = (Button) findViewById(R.id.btn_watch_trailer);

        m_txt_title.setText(mMovie.getTitle());
        m_txt_year.setText(mMovie.getReleaseDate());
        m_txt_desc.setText(mMovie.getOverview());


        loadImage(BACKDROP_URL_IMG +  mMovie.getBackdropPath(), m_img_bg);
        loadImage(POSTER_URL_IMG  + mMovie.getPosterPath(), m_img_poster);
    }

    private void loadImage (String url, final ImageView imageView) {
        Glide
                .with(getApplicationContext())
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // TODO: 08/11/16 handle failure
                        if (imageView.getId() == R.id.img_poster) {
                            mProgressPoster.setVisibility(View.GONE);
                        }
                        Log.e(TAG, " loadImage error:  " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // image ready, hide progress now
                        if (imageView.getId() == R.id.img_poster) {
                            mProgressPoster.setVisibility(View.GONE);
                        }
                        return false;   // return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade()
                //.placeholder((imageView.getId() == R.id.img_poster) ? R.drawable.poster : R.drawable.backdrop )
                .error((imageView.getId() == R.id.img_poster) ? android.R.drawable.ic_menu_report_image : R.drawable.detail_bg )
                .into(imageView);
    }
}
