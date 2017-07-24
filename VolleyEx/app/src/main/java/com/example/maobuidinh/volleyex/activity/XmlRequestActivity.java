package com.example.maobuidinh.volleyex.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.maobuidinh.volleyex.R;

public class XmlRequestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = XmlRequestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnXmlReq:
                xmlRequest();
                break;
        }
    }

    public void xmlRequest(){

    }
}
