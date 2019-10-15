package com.wishill.wishill.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.wishill.wishill.AppSignatureHelper;
import com.wishill.wishill.R;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    Dialog dialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        createAlert();
        checkInternet();

    }

    TextView errorContent;
    private void createAlert() {
        dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams")
        View alert_layout = factory.inflate(R.layout.retry_dialog, null);
        errorContent    = alert_layout.findViewById(R.id.error_content);
        TextView retry  = alert_layout.findViewById(R.id.tv_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                checkInternet();
            }
        });

        dialog.setContentView(alert_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(500, 400);
    }

    private void haveInternetPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            errorContent.setText("Unable to connect.\nCheck your internet permission.");
            dialog.show();
        } else {
            errorContent.setText("Data fetch failed.\nCheck your connectivity");
            dialog.show();
        }
    }

    private void checkInternet() {
//        try {
            if (isConnected()){
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
            } else {
                haveInternetPermission();
//                Toast.makeText(this, "Data fetch failed.\nCheck your connectivity", Toast.LENGTH_SHORT).show();
            }
       /* } catch (InterruptedException | IOException e) {
            errorContent.setText("Unknown error occurred.\nTry again");
            dialog.show();
//            Toast.makeText(this, "Unknown error occurred.\nTry again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/
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

    /*public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }*/

    /*public boolean isConnected(){
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            //You can replace it with your name
            return !ipAddr.toString().equals("");

        } catch (Exception e) {
            return false;
        }
    }*/

    // ICMP
    public boolean isConnected() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)          { e.printStackTrace(); }

        return false;
    }
}
