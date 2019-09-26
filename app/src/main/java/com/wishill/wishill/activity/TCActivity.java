package com.wishill.wishill.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.privacyPolicy.PrivacyPolicyAPI;
import com.wishill.wishill.api.recommendedColleges.privacyPolicy.PrivacyPolicyResponse;
import com.wishill.wishill.api.recommendedColleges.termsAndCondition.TermsAndConditionAPI;
import com.wishill.wishill.api.recommendedColleges.termsAndCondition.TermsAndConditionResponse;
import com.wishill.wishill.utilities.APILinks;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TCActivity extends AppCompatActivity {
    String from;
    TextView toolBarTitle;
    WebView webView;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        from = getIntent().getStringExtra("from");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolBarTitle=findViewById(R.id.toolbar_title);
        if(from.equals("tc")){
            toolBarTitle.setText("Terms and Conditions");
            url=APILinks.TERMS_AND_CONDITIONS;
        }else if(from.equals("pp")){
            toolBarTitle.setText("Privacy Policy");
            url=APILinks.PRIVACY_POLICY;
        }else if(from.equals("about")){
            toolBarTitle.setText("About Us");
            url=APILinks.ABOUT_US;
        }
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
