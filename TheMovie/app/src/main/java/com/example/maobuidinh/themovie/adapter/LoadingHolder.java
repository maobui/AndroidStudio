package com.example.maobuidinh.themovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maobuidinh.themovie.R;
import com.example.maobuidinh.themovie.util.PaginationAdapterCallback;

/**
 * Created by mao.buidinh on 8/8/2017.
 */

public class LoadingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ProgressBar mProgressBar;
    private ImageButton mRetryBtn;
    private TextView mErrorTxt;
    private LinearLayout mErrorLayout;

    private Context mContext;
    private PaginationAdapterCallback mCallback;

    public LoadingHolder(Context context, View itemView, PaginationAdapterCallback callback) {
        super(itemView);

        mContext = context;
        mCallback = callback;

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

                //showRetry(false, null); will call on retryPageLoad.
                mCallback.retryPageLoad();

                break;
        }
    }

    public void bind(String errorMsg, boolean retryPageLoad ) {
        if (retryPageLoad) {
            mErrorLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            mErrorTxt.setText( errorMsg != null ? errorMsg : mContext.getString(R.string.error_msg_unknown));
        } else {
            mErrorLayout.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
