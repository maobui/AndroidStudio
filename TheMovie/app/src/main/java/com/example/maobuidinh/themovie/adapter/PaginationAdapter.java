package com.example.maobuidinh.themovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.maobuidinh.themovie.util.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

import static com.example.maobuidinh.themovie.AppConstant.POSTER_URL_IMG;

/**
 * Created by maobuidinh on 7/13/2017.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PaginationAdapter.class.getSimpleName();

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Result> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public PaginationAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        movieResults = new ArrayList<>();
    }

    public List<Result> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Result> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Result result = movieResults.get(position); // Movie
        final int idx = position;

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.mPosterImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Log.e(TAG, "onClick ******************************* ");
                            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                            intent.putExtra("movie", movieResults.get(idx));
                            v.getContext().startActivity(intent);
                    }
                });

                movieVH.mRatting.setText(result.getVoteAverage().toString());
                movieVH.mMovieTitle.setText(result.getTitle());


                movieVH.mYear.setText(
                        result.getReleaseDate().substring(0, 4)  // we want the year only
                                + " | "
                                + result.getOriginalLanguage().toUpperCase()
                );
                movieVH.mMovieDesc.setText(result.getOverview());

                /**
                 * Using Glide to handle image loading.
                 * Learn more about Glide here:
                 * <a href="http://blog.grafixartist.com/image-gallery-app-android-studio-1-4-glide/" />
                 */
                Glide
                        .with(context)
                        .load(POSTER_URL_IMG + result.getPosterPath())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                // TODO: 08/11/16 handle failure
                                movieVH.mProgress.setVisibility(View.GONE);
                                Log.e(TAG, " loadImage error:  " + e.getMessage());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                // image ready, hide progress now
                                movieVH.mProgress.setVisibility(View.GONE);
                                return false;   // return false if you want Glide to handle everything else.
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                        .centerCrop()
                        .crossFade()
                        //.placeholder(R.drawable.poster) // can also be a drawable first until to show real image.
                        .error(android.R.drawable.ic_menu_report_image) // will be displayed if the image cannot be loaded
                        .into(movieVH.mPosterImg);

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }

                break;
        }

    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Result r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Result> moveResults) {
        for (Result result : moveResults) {
            add(result);
        }
    }

    public void remove(Result r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Result());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Result getItem(int position) {
        return movieResults.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(movieResults.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{
        private TextView mRatting;
        private TextView mMovieTitle;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);

            mRatting = (TextView) itemView.findViewById(R.id.rating);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieDesc = (TextView) itemView.findViewById(R.id.movie_desc);
            mYear = (TextView) itemView.findViewById(R.id.movie_year);
            mPosterImg = (ImageView) itemView.findViewById(R.id.movie_poster);
            mProgress = (ProgressBar) itemView.findViewById(R.id.movie_progress);

            // Listener for all item_list layout.
            //itemView.setOnClickListener(this);

            // Listener for only movie_poster.
//            mPosterImg.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            Log.e(TAG, "onClick ******************************* "+  (v.getId() == R.id.movie_poster));
//            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
//            intent.putExtra("movie", movieResults.get(0));
//            v.getContext().startActivity(intent);
//        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

}
