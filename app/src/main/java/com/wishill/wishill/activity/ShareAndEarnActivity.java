package com.wishill.wishill.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wishill.wishill.R;

import java.io.IOException;

public class ShareAndEarnActivity extends AppCompatActivity {

    TextView copyBtn, shareCodeTV;
    ImageView whatsappBtn, shareBtn;

    String inviteCode = "";
    String shareBody = "Download Now !! \nhttps://play.google.com/store/apps/details?id=com.wishill.wishill";
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
        shareBtn    = findViewById(R.id.share_btn);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        inviteCode = sharedPreferences.getString("shareCode", "TEMPCODE12");

        shareCodeTV.setText(inviteCode);
        if (!inviteCode.equals("TEMPCODE12")){
            shareBody = shareBody+"&referrer="+inviteCode;
        }
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Invite code", inviteCode);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ShareAndEarnActivity.this, "Invite code copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        whatsappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getIntent().hasExtra("image")){
                    Glide.with(ShareAndEarnActivity.this)

                            .load(getIntent().getStringExtra("image"))
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    sendShareToWhatsApp(resource);
                                }

                            });
                }else {
                    sendShareToWhatsAppIntent();
                }
                //shareWithWhatsapp();
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("image")){
                    Glide.with(ShareAndEarnActivity.this)

                            .load(getIntent().getStringExtra("image"))
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    shareInviteCode(resource);
                                }

                            });
                }else {
                    shareInviteCode();
                }

            }
        });
    }

    private void shareInviteCode() {
        /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invite code");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share invite code"));*/

        Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "ic_launcher_logo");
        //setup intent:
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        //setup image extra, if exists:
        Bitmap picBitmap = null;
        try {
            picBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (picBitmap != null) {
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), picBitmap, "", "");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            sharingIntent.setType("*/*");
        } else {
            //if no picture, just text set - this MIME
            sharingIntent.setType("text/plain");
        }

        //setup sharing message
        String message = "Wishil App \nShare and Earn - Use code "+inviteCode +" to get a guaranteed scholarships through wishill for course booking.\n"+shareBody;

        sharingIntent.putExtra(Intent.EXTRA_TEXT, message.toString());



        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sharingIntent, "Share invite code"));
        } else {
            Log.w("", "sendShareIntent: cant resolve intent");
            Toast.makeText(this, "whatsapp not installed", Toast.LENGTH_SHORT).show();
        }
    }
    private void shareInviteCode(Bitmap resource) {
        /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invite code");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share invite code"));*/


        Intent sharingIntent = new Intent(Intent.ACTION_SEND);



        if (resource != null) {
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", "");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            sharingIntent.setType("*/*");
        } else {
            //if no picture, just text set - this MIME
            sharingIntent.setType("text/plain");
        }

        //setup sharing message
        String message = getIntent().getStringExtra("instituteName")+"\nWishil App \nShare and Earn - Use code "+inviteCode +" to get a guaranteed scholarships through wishill for course booking.\n"+shareBody;

        sharingIntent.putExtra(Intent.EXTRA_TEXT, message.toString());



        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sharingIntent, "Share invite code"));
        } else {
            Log.w("", "sendShareIntent: cant resolve intent");
            Toast.makeText(this, "whatsapp not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareWithWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "WhatsApp have not been installed.", Toast.LENGTH_SHORT).show();
        }

        /*
         Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "background_image_two");
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, Util.shareImage(ShareAndEarnActivity.this,bitmap));
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(whatsappIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    public void sendShareToWhatsApp(Bitmap bitmap) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


        if (bitmap != null) {
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            sharingIntent.setType("*/*");
        } else {
            //if no picture, just text set - this MIME
            sharingIntent.setType("text/plain");
        }

        //setup sharing message
        String message = getIntent().getStringExtra("instituteName")+"\nWishil App \nShare and Earn - Use code "+inviteCode +" to get a guaranteed scholarships through wishill for course booking.\n"+shareBody;

        sharingIntent.putExtra(Intent.EXTRA_TEXT, message.toString());

        //target WhatsApp:
        sharingIntent.setPackage("com.whatsapp");


        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sharingIntent);
        } else {
            Log.w("", "sendShareIntent: cant resolve intent");
            Toast.makeText(this, "whatsapp not installed", Toast.LENGTH_SHORT).show();
        }

    }
    public void sendShareToWhatsAppIntent() {
        Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "ic_launcher_logo");
        //setup intent:
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        //setup image extra, if exists:
        Bitmap picBitmap = null;
        try {
            picBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (picBitmap != null) {
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), picBitmap, "", "");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            sharingIntent.setType("*/*");
        } else {
            //if no picture, just text set - this MIME
            sharingIntent.setType("text/plain");
        }

        //setup sharing message
        String message = "Wishil App \nShare and Earn - Use code "+inviteCode +" to get a guaranteed scholarships through wishill for course booking.\n"+shareBody;

        sharingIntent.putExtra(Intent.EXTRA_TEXT, message.toString());

        //target WhatsApp:
        sharingIntent.setPackage("com.whatsapp");


        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sharingIntent);
        } else {
            Log.w("", "sendShareIntent: cant resolve intent");
            Toast.makeText(this, "whatsapp not installed", Toast.LENGTH_SHORT).show();
        }

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
