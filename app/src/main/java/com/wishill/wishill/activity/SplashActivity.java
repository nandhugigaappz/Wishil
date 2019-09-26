package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.wishill.wishill.AppSignatureHelper;
import com.wishill.wishill.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        printKeyHash();
        getSignature();
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        int TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getString("firstrun", "true").equals("false")){
                    Intent toSlider = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(toSlider);
                    finish();
                }else{
                    Intent toSlider = new Intent(SplashActivity.this, SocialMediaActivity.class);
                    startActivity(toSlider);
                    finish();
                }
            }
        }, TIME_OUT);
    }

    private void getSignature() {
        AppSignatureHelper appSignatureHelper=new AppSignatureHelper(SplashActivity.this);
        ArrayList<String> signatureList=appSignatureHelper.getAppSignatures();
        Log.e("Signature", signatureList.get(0));

      /*  Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:9947654469"));
        intent.putExtra("sms_body",  signatureList.get(0));
        intent.putExtra("exit_on_sent", true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 101);
        }*/
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.wishill.wishill", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("HashKey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
