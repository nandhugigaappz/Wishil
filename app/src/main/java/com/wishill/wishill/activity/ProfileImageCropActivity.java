package com.wishill.wishill.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lyft.android.scissors.CropView;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.partnershipwithwishill.PartnerEditProfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ProfileImageCropActivity extends AppCompatActivity {
    CropView crop;
    Button btn_cancel, btn_save;
    Bitmap croppedBitmap, finalbitmap;
    String final_img_path;
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_crop);
        crop = findViewById(R.id.crop);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        from=getIntent().getStringExtra("from");
        try {
            if(from.equals("normal")){
                crop.setImageBitmap(NormalUserEditProfileActivity.bt);
            }else{
                crop.setImageBitmap(PartnerEditProfile.bt);
            }
        } catch (Exception e) {
            Log.e("Error", e + "");
            Toast.makeText(ProfileImageCropActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                croppedBitmap = crop.crop();
                finalbitmap = getResizedBitmap(croppedBitmap, 350, 350);
                Calendar calender = Calendar.getInstance();
                Long lo = calender.getTimeInMillis();
                savePhoto(finalbitmap, lo + ".png");
                finish();
            }
        });
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public void savePhoto(Bitmap imaginative, String filenameone) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, filenameone);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            imaginative.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final_img_path = directory.getAbsolutePath() + "/" + filenameone;
        if(from.equals("normal")){
            NormalUserEditProfileActivity.selectedFinalImageUrl = final_img_path;
        }else{
            PartnerEditProfile.selectedFinalImageUrl = final_img_path;
        }
    }

}
