package com.example.maobuidinh.iapads;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.RemoteException;
import java.util.Collections;
import java.util.List;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.purchase.InAppPurchaseResult;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;


import com.android.vending.billing.IInAppBillingService;

public class MainActivity extends Activity implements PlayStorePurchaseListener {

    public static final int BILLING_RESPONSE_RESULT_OK = 0;

    private InterstitialAd mInterstitialAd;
    private IInAppBillingService mService;
    private Button btnShowAds;
    private String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        btnShowAds = (Button) findViewById(R.id.btnShowAds);
        btnShowAds.setEnabled(false);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2412876219430673/4729615340");//IAP interstitial
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//interstitial


        mInterstitialAd.setPlayStorePurchaseParams(this, null);
        setInterstitalListener();


    }

    private void setInterstitalListener()
    {

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.i("INTERSTITAL", "========================= onAdClosed");
                super.onAdClosed();
                btnShowAds.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                Log.i("INTERSTITAL", "========================= onAdLoaded");
                super.onAdLoaded();
                btnShowAds.setEnabled(true);
            }
        });
    }

    public  void loadInterstitial(View v)
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(androidId.toString())
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public  void displayInterstitial(View v)
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }
    @Override
    public boolean isValidPurchase(String sku) {
        // Optional: check if the product has already been purchased.
        try {
            if (getOwnedProducts().contains(sku)) {
                // Handle the case if product is already purchased.
                return false;
            }
        } catch (RemoteException e) {
            Log.e("Iap-Ad", "Query purchased product failed.", e);
            return false;
        }
        return true;
    }

    @Override
    public void onInAppPurchaseFinished(InAppPurchaseResult result) {
        Log.i("Iap-Ad", "onInAppPurchaseFinished Start");
        int resultCode = result.getResultCode();
        Log.i("Iap-Ad", "result code: " + resultCode);
        String sku = result.getProductId();
        if (resultCode == Activity.RESULT_OK) {
            Log.i("Iap-Ad", "purchased product id: " + sku);
            int responseCode = result.getPurchaseData().getIntExtra(
                    "RESPONSE_CODE", BILLING_RESPONSE_RESULT_OK);
            String purchaseData = result.getPurchaseData().getStringExtra("INAPP_PURCHASE_DATA");
            Log.i("Iap-Ad", "response code: " + responseCode);
            Log.i("Iap-Ad", "purchase data: " + purchaseData);

            // Finish purchase and consume product.
            result.finishPurchase();
            // if (responseCode == BILLING_RESPONSE_RESULT_OK) {
            // Optional: your custom process goes here, e.g., add coins after purchase.
            //  }
        } else {
            Log.w("Iap-Ad", "Failed to purchase product: " + sku);
        }
        Log.i("Iap-Ad", "onInAppPurchaseFinished End");
    }

    private List<String> getOwnedProducts() throws RemoteException {
        // Query for purchased items.
        // See http://developer.android.com/google/play/billing/billing_reference.html and
        // http://developer.android.com/google/play/billing/billing_integrate.html
        Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
        int response = ownedItems.getInt("RESPONSE_CODE");
        Log.i("Iap-Ad", "Response code of purchased item query");
        if (response == 0) {
            return ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
        }
        return Collections.emptyList();
    }
}
