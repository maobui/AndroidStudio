package com.example.maobuidinh.glideimage.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;


/**
 * Created by maobuidinh on 6/19/2017.
 *
 * Scroll Listener for RecyclerView.
 * https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = EndlessRecyclerViewScrollListener.class.getSimpleName();

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 1;// page start from 1 not 0;
    // Custom  LinearLayoutManager
    private static int totalItemsInView;
    private static int defaultItemHeight;
    private static int featuredItemHeight;
    private static int maxDistance;
    private static int diffHeight;

    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        initAttrLinearLayout();
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

            // Hack RecyclerView.
            if (((LinearLayoutManager) mLayoutManager).getOrientation() == LinearLayoutManager.VERTICAL)
            {
                totalItemsInView = mLayoutManager.getItemCount();
                changeHeightAccordingToScroll(recyclerView);
            }
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            Log.d(TAG, "*********** call onLoadMore page " + currentPage);
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    private void changeHeightAccordingToScroll(RecyclerView recyclerView) {
        Log.d(TAG, "*********** call changeHeightAccordingToScroll ");
        for (int i = 0; i < totalItemsInView; i++) {
            View viewToBeResized = recyclerView.getChildAt(i);
            if (viewToBeResized != null) {
                float distance = getTopOfView(viewToBeResized);
                //Log.d(TAG, "*********** call changeHeightAccordingToScroll distance : " + distance);
                if (distance > maxDistance) {
                    viewToBeResized.getLayoutParams().height = defaultItemHeight;
                    viewToBeResized.requestLayout();
                } else if (distance <= maxDistance) {
                    viewToBeResized.getLayoutParams().height = (int) height(distance);
                    viewToBeResized.requestLayout();
                }
                //Log.d(TAG, "*********** call changeHeightAccordingToScroll viewToBeResized.getLayoutParams().height : " + viewToBeResized.getLayoutParams().height);
            }
        }
    }

    private  void initAttrLinearLayout(){
        totalItemsInView = 0;
        defaultItemHeight = 300;
        featuredItemHeight = 700;
        maxDistance = featuredItemHeight;
        diffHeight = featuredItemHeight - defaultItemHeight;
    }
    private float getTopOfView(View view) {
        return Math.abs(view.getTop());
    }

    private float height(float distance) {
        return featuredItemHeight - ((distance * (diffHeight)) / maxDistance);
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
