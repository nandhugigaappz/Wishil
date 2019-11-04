package com.wishill.wishill.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.utilities.APILinks;

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
