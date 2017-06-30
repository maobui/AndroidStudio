package com.example.maobuidinh.glideimage.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by maobuidinh on 6/17/2017.
 *
 * Scroll Listener for RecyclerView.
 */

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = PaginationScrollListener.class.getSimpleName();

    private boolean isLoading;
    private boolean hasMorePages;
    private int currentPage = 0;
    private int startingPageIndex = 1;
    private RefreshList refreshList;
    private boolean isRefreshing;
    private int pastVisibleItems;

    // Custom  LinearLayoutManager
    private static int totalItemsInView;
    private static int defaultItemHeight;
    private static int featuredItemHeight;
    private static int maxDistance;
    private static int diffHeight;

    public PaginationScrollListener(RefreshList refreshList, int currentPage) {
        this.isLoading = false;
        this.hasMorePages = true;
        this.currentPage = currentPage;
        this.refreshList = refreshList;
        initAttrLinearLayout();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager  = (StaggeredGridLayoutManager) mLayoutManager;
            int[] firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
            if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                pastVisibleItems = firstVisibleItems[0];
            }
        } else if (mLayoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
            pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;
            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

            // Hack RecyclerView.
            if (((LinearLayoutManager) mLayoutManager).getOrientation() == LinearLayoutManager.VERTICAL)
            {
                totalItemsInView = mLayoutManager.getItemCount();
                changeHeightAccordingToScroll(recyclerView);
            }
        }

        if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
//            Log.d(TAG, "*********** visibleItemCount : " + visibleItemCount + " pastVisibleItems: " + pastVisibleItems
//                    + " totalItemCount : " + totalItemCount);
//            Log.d(TAG, "*********** end of page : " + this.currentPage);
            isLoading = true;
            if (hasMorePages && !isRefreshing){
                isRefreshing = true;
                Log.d(TAG, "*********** call onRefresh page " + currentPage);
                refreshList.onRefresh(currentPage);
            }
        } else {
            isLoading = false;
        }

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


    public void noMorePages(){
        this.hasMorePages = false;
    }

    public void notifyMorePages(){
        isRefreshing = false;
        currentPage ++;
    }

    public  int getcurrentPage() {
        return this.currentPage;
    }

    public void resetState(){
        this.currentPage = startingPageIndex;
        this.isLoading = false;
        this.hasMorePages = true;

    }
    public interface RefreshList {
        void onRefresh(int currentPage);
    }
}
