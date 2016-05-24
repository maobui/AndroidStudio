package com.example.maobuidinh.adsbanner;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private AdView mAdview;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtHello = (TextView) findViewById(R.id.txtHello);
        final String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        txtHello.setText(androidId.toString());

        Button btnShowInters = (Button) findViewById(R.id.btnShowInters);


        //Banner
        mAdview = (AdView) findViewById(R.id.adView);
        if (mAdview.getAdSize() == null){
            mAdview.setAdSize(AdSize.SMART_BANNER);
        }
//        mAdview = new AdView(this);
//        mAdview.setAdSize(AdSize.SMART_BANNER);
//        mAdview.setAdUnitId(getString(R.string.banner_ad_unit_id));

        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("6E0292B5521293D4")
                .addTestDevice(androidId.toString())
                .build();

        mAdview.loadAd(adRequest);

        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.i("BANNER", "========================= onAdClosed");
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i("BANNER", "========================= onAdFailedToLoad");
                super.onAdFailedToLoad(errorCode);
                switch (errorCode)
                {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        Log.i("BANNER", "========================= ERROR_CODE_INTERNAL_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        Log.i("BANNER", "========================= ERROR_CODE_INVALID_REQUEST");
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        Log.i("BANNER", "========================= ERROR_CODE_NETWORK_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        Log.i("BANNER", "========================= ERROR_CODE_NO_FILL");
                        break;
                }
            }

            @Override
            public void onAdLeftApplication() {
                Log.i("BANNER", "========================= onAdLeftApplication");
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                Log.i("BANNER", "========================= onAdOpened");
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                Log.i("BANNER", "========================= onAdLoaded");
                super.onAdLoaded();
            }
        });

        //Interstitial
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        btnShowInters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(androidId.toString())
                        .build();

                mInterstitialAd.loadAd(adRequest);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resume the AdView.
        mAdview.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        mAdview.pause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        mAdview.destroy();

        super.onDestroy();
    }
}
