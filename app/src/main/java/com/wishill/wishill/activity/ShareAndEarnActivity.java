package com.wishill.wishill.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wishill.wishill.R;

public class ShareAndEarnActivity extends AppCompatActivity {

    TextView copyBtn, shareCodeTV;
    ImageView whatsappBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_and_earn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        copyBtn = findViewById(R.id.copy_button);
        shareCodeTV = findViewById(R.id.share_code_text);
        whatsappBtn = findViewById(R.id.whatsapp_btn);

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Invite code", shareCodeTV.getText().toString());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ShareAndEarnActivity.this, "Invite code copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        whatsappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWithWhatsapp();
            }
        });
    }

    private void shareWithWhatsapp() {

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
