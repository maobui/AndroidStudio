package com.example.maobuidinh.adsinterstitial;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private Button btnShowInters;
    private String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.i("INTERSTITAL", "========================= onAdClosed");
                super.onAdClosed();
                startApp();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i("INTERSTITAL", "========================= onAdFailedToLoad");
                super.onAdFailedToLoad(errorCode);
                switch (errorCode)
                {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        Log.i("INTERSTITAL", "========================= ERROR_CODE_INTERNAL_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        Log.i("INTERSTITAL", "========================= ERROR_CODE_INVALID_REQUEST");
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        Log.i("INTERSTITAL", "========================= ERROR_CODE_NETWORK_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        Log.i("INTERSTITAL", "========================= ERROR_CODE_NO_FILL");
                        break;
                }
            }

            @Override
            public void onAdLeftApplication() {
                Log.i("INTERSTITAL", "========================= onAdLeftApplication");
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                Log.i("INTERSTITAL", "========================= onAdOpened");
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                Log.i("INTERSTITAL", "========================= onAdLoaded");
                super.onAdLoaded();
            }
        });

        btnShowInters= (Button) findViewById(R.id.btnShowInters);
        btnShowInters.setVisibility(View.INVISIBLE);
        btnShowInters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstital();
            }
        });

        startApp();


    }

    public void showInterstital()
    {
        if(mInterstitialAd != null && mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }

    public void startApp()
    {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded())
        {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(androidId.toString())
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
        btnShowInters.setVisibility(View.VISIBLE);
    }

}
