package com.example.maobuidinh.volleyex.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.maobuidinh.volleyex.R;
import com.example.maobuidinh.volleyex.app.AppConstant;
import com.example.maobuidinh.volleyex.app.AppController;


public class XmlRequestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = XmlRequestActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private Button btnXmlReg;
    private TextView txtMsgXmlResponse;
    // This tag will be used to cancel the request
    private String tag_xml_req = "xml_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_request);

        btnXmlReg = (Button) findViewById(R.id.btnXmlReq);
        txtMsgXmlResponse = (TextView) findViewById(R.id.msgXmlResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnXmlReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnXmlReq:
                makeXmlReq();
                break;
        }
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     * */
    private void makeXmlReq() {
        showProgressDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConstant.URL_XML_RESPONE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                // Parse xml here ...
                txtMsgXmlResponse.setText(response.toString());
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestqQueue(strReq, tag_xml_req);

    }
}
