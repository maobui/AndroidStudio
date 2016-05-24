package com.example.maobuidinh.dynamicfragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by mao.buidinh on 5/24/2016.
 */
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "DetailsActivity", Toast.LENGTH_SHORT).show();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

            // create fragment
            DetailsFragment details = new DetailsFragment();

            // get and set the position input by user (i.e., "index")
            // which is the construction arguments for this fragment
            details.setArguments(getIntent().getExtras());

            //
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();
        }
    }
}
