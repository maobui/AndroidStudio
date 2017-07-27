package com.example.maobuidinh.wallpapers.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.maobuidinh.wallpapers.R;
import com.example.maobuidinh.wallpapers.app.AppController;
import com.example.maobuidinh.wallpapers.fragment.PhotoFragment;
import com.example.maobuidinh.wallpapers.picasa.model.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int ITEM_ABLUM_ID = 9999;
    // Navigation drawer
    private List<Category> mCategoryList;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // init Navigation drawer
        initNavigationDrawer();

        if (savedInstanceState == null){
            // first time display first nav.
            displayView(0);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == ITEM_ABLUM_ID) {

        }*/

        for(int i = 0; i < mCategoryList.size(); i ++){
            if( id == (ITEM_ABLUM_ID + i)){
                displayView(i);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Init menu nav.
     */
    private void initNavigationDrawer(){
        // Getting the albums from shared preferences
        mCategoryList = AppController.getInstance().getPrefManager().getCategories();
        Menu menu = mNavigationView.getMenu();
        menu.clear();

        // Insert "Recently Added" in navigation drawer first position
        Category recentAlbum = new Category(null,
                getString(R.string.nav_drawer_recently_added));
        mCategoryList.add(0, recentAlbum);

        // Add all albums to menu nav.
        Category category;
        for (int i = 0; i < mCategoryList.size(); i ++){
            category = mCategoryList.get(i);
            menu.add(R.id.group_albums, ITEM_ABLUM_ID + i, i, category.getTitle()).setIcon(R.drawable.ic_menu_camera);
        }
    }

    /**
     * Display fragment view when click on item of nav.
     * @param position : index in album.
     */
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position){
            case 0 :
                // Display 'Recently Added' fragment
                // this album has no id .
                fragment = PhotoFragment.newInstance(null);
                break;
            default:
                fragment = PhotoFragment.newInstance(mCategoryList.get(position).getId());
                break;
        }

        if (fragment != null){
            Log.e(TAG, "Display fragment " + position);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // Set title
            getSupportActionBar().setTitle(mCategoryList.get(position).getTitle());
        } else {
            Log.e(TAG, getString(R.string.msg_error_load_fragment));
        }
    }
}
