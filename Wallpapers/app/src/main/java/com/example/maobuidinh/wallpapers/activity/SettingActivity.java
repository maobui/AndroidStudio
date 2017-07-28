package com.example.maobuidinh.wallpapers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maobuidinh.wallpapers.R;
import com.example.maobuidinh.wallpapers.util.PrefManager;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG=SettingActivity.class.getSimpleName();

    private PrefManager mPrefManager;
    private TextView txtGoogleUsername, txtNoOfGridColumns, txtGalleryName;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        txtGoogleUsername = (TextView) findViewById(R.id.txtGoogleUsername);
        txtNoOfGridColumns = (TextView) findViewById(R.id.txtNoOfColumns);
        txtGalleryName = (TextView) findViewById(R.id.txtGalleryName);
        btnSave = (Button) findViewById(R.id.btnSave);

        mPrefManager = new PrefManager(getApplicationContext());

        // Display edittext values stored in shared preferences
        // Google username
        txtGoogleUsername.setText(mPrefManager.getGoogleUserName());

        // Number of grid columns
        txtNoOfGridColumns.setText(String.valueOf(mPrefManager.getNoOfGridColumns()));

        // Gallery name
        txtGalleryName.setText(mPrefManager.getGalleryName());

        // Save settings button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Validating the data before saving to shared preferences
                // validate google username
                String googleUsername = txtGoogleUsername.getText().toString().trim();
                if (googleUsername.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_enter_google_username),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // validate number of grid columns
                String no_of_columns = txtNoOfGridColumns.getText().toString().trim();
                if (no_of_columns.length() == 0 || !isInteger(no_of_columns)) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_enter_valid_grid_columns),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // validate gallery name
                String galleryName = txtGalleryName.getText().toString().trim();
                if (galleryName.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_enter_gallery_name),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Check for setting changes
                if (!googleUsername.equalsIgnoreCase(mPrefManager.getGoogleUserName())
                        || !no_of_columns.equalsIgnoreCase(String.valueOf(mPrefManager.getNoOfGridColumns()))
                        || !galleryName.equalsIgnoreCase(mPrefManager.getGalleryName())) {
                    // User changed the settings
                    // save the changes and launch SplashScreen to initialize
                    // the app again
                    mPrefManager.setGoogleUsername(googleUsername);
                    mPrefManager.setNoOfGridColumns(Integer.parseInt(no_of_columns));
                    mPrefManager.setGalleryName(galleryName);

                    // start the app from SplashScreen
                    Intent i = new Intent(SettingActivity.this, SplashActivity.class);
                    // Clear all the previous activities
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // user not modified any values in the form
                    // skip saving to shared preferences
                    // just go back to previous activity
                    onBackPressed();
                }

            }
        });

    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
