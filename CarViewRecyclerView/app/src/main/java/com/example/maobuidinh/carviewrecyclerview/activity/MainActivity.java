package com.example.maobuidinh.carviewrecyclerview.activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.maobuidinh.carviewrecyclerview.R;
import com.example.maobuidinh.carviewrecyclerview.adapter.AlbumsAdapter;
import com.example.maobuidinh.carviewrecyclerview.model.Album;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    // num of picture of album.
    private static final int ALBUM_SIZE = 20;
    //num of the columns.
    private final int SPANCOUNT = 2;
    //space of 2 columns.
    private final int SPACING = 10;

    private static boolean is_staggered_grid_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        Log.d(TAG, "onCreate SharedPreferenceChanged staggered_grid_layout : " + is_staggered_grid_layout);
        setLayoutManager(is_staggered_grid_layout);
        recyclerView.setAdapter(adapter);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.OnSharedPreferenceChangeListener listener =
//                new SharedPreferences.OnSharedPreferenceChangeListener() {
//                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
//                        // listener implementation
//                        is_staggered_grid_layout  = prefs.getBoolean(SettingsActivity.STAGGERED_GRID_LAYOUT, false);
//                        Log.d(TAG, "onCreate OnSharedPreferenceChangeListener is_staggered_grid_layout *** : " + is_staggered_grid_layout);
//                    }
//                };
//        prefs.registerOnSharedPreferenceChangeListener(listener);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
//        int[] covers = new int[]{
//                R.drawable.album1,
//                R.drawable.album2,
//                R.drawable.album3,
//                R.drawable.album4,
//                R.drawable.album5,
//                R.drawable.album6,
//                R.drawable.album7,
//                R.drawable.album8,
//                R.drawable.album9,
//                R.drawable.album10,
//                R.drawable.album11,
//                R.drawable.album12,
//                R.drawable.album13};
        int [] covers = new int [ALBUM_SIZE];
        for (int i = 0; i < ALBUM_SIZE; i++){
            int temp = i + 1;
            covers[i] = getResources().getIdentifier("album" + temp, "drawable", getPackageName() );
        }

        Album a = new Album("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new Album("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new Album("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new Album("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new Album("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        // Special image (album19, album20 )has w x h smaller than normal image
        a = new Album("Honeymoon", 14, covers[18]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[19]);
        albumList.add(a);

        a = new Album("Loud", 11, covers[6]);
        albumList.add(a);

        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);

        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);

        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        a = new Album("Top Hits", 17, covers[10]);
        albumList.add(a);

        a = new Album("King Hits", 17, covers[11]);
        albumList.add(a);

        a = new Album("VIP Hits", 17, covers[12]);
        albumList.add(a);

        a = new Album("True Romance", 13, covers[13]);
        albumList.add(a);

        a = new Album("Xscpae", 8, covers[14]);
        albumList.add(a);

        a = new Album("Maroon 5", 11, covers[15]);
        albumList.add(a);

        a = new Album("Born to Die", 12, covers[16]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[17]);
        albumList.add(a);

        // Special image (album19, album20 )has w x h smaller than normal image
        a = new Album("Honeymoon", 14, covers[18]);
        albumList.add(a);

        a = new Album("Honeymoon", 14, covers[19]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        is_staggered_grid_layout  = sharedPref.getBoolean(SettingsActivity.STAGGERED_GRID_LAYOUT, false);
        Log.d(TAG, "onRestart SharedPreferenceChanged staggered_grid_layout : " + is_staggered_grid_layout);
        setLayoutManager(is_staggered_grid_layout);
    }

    public void setLayoutManager (boolean is)
    {
        RecyclerView.LayoutManager mLayoutManager;
        recyclerView.setHasFixedSize(true);
        if (is) {
            mLayoutManager = new StaggeredGridLayoutManager(SPANCOUNT + 1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            mLayoutManager = new GridLayoutManager(this, SPANCOUNT);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPANCOUNT, dpToPx(SPACING), true));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }
}
