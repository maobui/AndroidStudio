package com.example.maobuidinh.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsPrefActivity.class));
                break;
            case R.id.action_settings_headers:
                startActivity(new Intent(MainActivity.this, SettingsPrefHeadersActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
