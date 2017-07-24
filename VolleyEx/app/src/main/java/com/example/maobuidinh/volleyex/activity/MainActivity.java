package com.example.maobuidinh.volleyex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maobuidinh.volleyex.R;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnJsonRequest;
    private Button btnXmlRequest;
    private Button btnStringRequest;
    private Button btnImageRequest;
    private TextView txtResult;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJsonRequest = (Button) findViewById(R.id.btnJsonRequest);
        btnXmlRequest = (Button) findViewById(R.id.btnXmlRequest);
        btnStringRequest = (Button) findViewById(R.id.btnStringRequest);
        btnImageRequest = (Button) findViewById(R.id.btnImageRequest);
        txtResult = (TextView) findViewById(R.id.txtResult);

        btnJsonRequest.setOnClickListener(this);
        btnXmlRequest.setOnClickListener(this);
        btnStringRequest.setOnClickListener(this);
        btnImageRequest.setOnClickListener(this);
        txtResult.setText("...");
    }

    @Override
    public void onClick(View v) {
        txtResult.setText("...");
        switch (v.getId()){
            case R.id.btnJsonRequest:
                mIntent = new Intent(MainActivity.this, JsonRequestActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnXmlRequest:
                mIntent = new Intent(MainActivity.this, XmlRequestActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnStringRequest:
                mIntent = new Intent(MainActivity.this, StringRequestActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnImageRequest:
                mIntent = new Intent(MainActivity.this, ImageRequestActivity.class);
                startActivity(mIntent);
                break;

        }
    }


}
