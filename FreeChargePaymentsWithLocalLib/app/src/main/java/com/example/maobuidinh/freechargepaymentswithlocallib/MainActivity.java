package com.example.maobuidinh.freechargepaymentswithlocallib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import in.freecharge.checkout.android.FreeChargePaymentSdk;
import in.freecharge.checkout.android.commons.FreechargeSdkEnvironment;
import in.freecharge.checkout.android.exception.FreechargeSdkException;
import in.freecharge.checkout.android.handler.FreeChargeAddMoneyCallback;
import in.freecharge.checkout.android.handler.FreeChargePaymentCallback;


/*
// Add from src : https://bitbucket.org/freecharge/paywithfreecharge-android/src
//
 */
public class MainActivity extends Activity {

    Button btnPay, btnAddMoney;
    Activity mActivity = this;

    Map<String, String> checkoutRequestMap;
    Map<String, String> addMoneyRequestHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FreeChargePaymentSdk.init(this, FreechargeSdkEnvironment.SANDBOX);

        checkoutRequestMap = new HashMap<>();
        checkoutRequestMap.put("merchantId","Your Merchant id");
        checkoutRequestMap.put("merchantTxnId","transaction id");
        checkoutRequestMap.put("amount", "transaction amount");
        checkoutRequestMap.put("furl", "fail url (if hosted else put dummy url)");
        checkoutRequestMap.put("surl", "success url (if hosted else put dummy url)");
        checkoutRequestMap.put("amount", "transaction amount");
        checkoutRequestMap.put("productInfo", "auth");
        checkoutRequestMap.put("customerName", "customer/user name if available");
        checkoutRequestMap.put("email", "customer/user email if available");
        checkoutRequestMap.put("mobile",  "customer/user mobile if available");
        checkoutRequestMap.put("channel", "ANDROID");
        checkoutRequestMap.put("checksum", "Generated from merchant backend");

        addMoneyRequestHashMap = new HashMap<>();
        addMoneyRequestHashMap.put("merchantId", "Your Merchant id");
        addMoneyRequestHashMap.put("amount","transaction amount");
        addMoneyRequestHashMap.put("channel", "ANDROID");
        addMoneyRequestHashMap.put("callbackUrl","https://test.com your callback url or a dummy url");
        addMoneyRequestHashMap.put("loginToken", "login token of user");
        addMoneyRequestHashMap.put("metadata","your meta data");
        addMoneyRequestHashMap.put("checksum", "Generated from merchant backend");

        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FreeChargePaymentSdk.startSafePayment(mActivity, checkoutRequestMap, freeChargePaymentCallback);
            }
        });

        btnAddMoney = (Button) findViewById(R.id.btnAddMoney);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FreeChargePaymentSdk.addMoney(mActivity, addMoneyRequestHashMap, freeChargeAddMoneyCallback);
            }
        });
    }

    final FreeChargePaymentCallback freeChargePaymentCallback = new FreeChargePaymentCallback() {

        @Override
        public void onTransactionFailed(HashMap<String, String> txnFailResponse) {
            Toast.makeText(mActivity.getApplicationContext(), txnFailResponse.get("errorMessage"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTransactionCancelled() {
            Toast.makeText(mActivity.getApplicationContext(), "user cancelled the transaction", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTransactionSucceeded(HashMap<String, String> txnSuccessResponse) {
            Toast.makeText(mActivity.getApplicationContext(),txnSuccessResponse.get("status"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorOccurred(FreechargeSdkException sdkError) {
            Toast.makeText(mActivity.getApplicationContext(), sdkError.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    final FreeChargeAddMoneyCallback freeChargeAddMoneyCallback = new FreeChargeAddMoneyCallback() {

        @Override
        public void onTransactionFailed(HashMap<String, String> txnFailResponse) {
            Toast.makeText(getApplicationContext(), txnFailResponse.get("errorMessage"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTransactionCancelled() {
            Toast.makeText(getApplicationContext(), "user cancelled the transaction", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTransactionSucceeded(HashMap<String, String> txnSuccessResponse) {
            Toast.makeText(getApplicationContext(),txnSuccessResponse.get("status"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorOccurred(FreechargeSdkException sdkError) {
            Toast.makeText(getApplicationContext(), sdkError.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
