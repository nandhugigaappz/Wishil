package com.wishill.wishill.activity.partnershipwithwishill;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.NormalUserEditProfileActivity;
import com.wishill.wishill.activity.NormalUserProfileImageBrowse;
import com.wishill.wishill.activity.ProfileImageCropActivity;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.CircleTransform;
import com.wishill.wishill.utilities.DialogProgress;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PartnerEditProfile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String userID;
    String userType;
    String userTypeId;
    DialogProgress dialogProgress;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    EditText edtUserName;
    ImageView ivProfile;
    EditText edtEmailAddress;
    EditText edtMobile;
  /*  EditText edtPasswordOne;
    EditText edtPasswordTwo;*/
    TextView tvBtnSubmit;

    String name;
    String email;
    String mobile;
  /*  String passwordOne;
    String passwordTwo;*/

    String currentProfileImage;
    public  static  String selectedFinalImageUrl="";
    public static String selectedImageUrl = "";
    static final int ID_GALLERY = 1;
    static final int ID_CROP = 2;
    public static Bitmap bt;
    MyThread t1;
    String profilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(PartnerEditProfile.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", "");
        userType = sharedPreferences.getString("userType", "");
        userTypeId = sharedPreferences.getString("userTypeId", "");
        currentProfileImage=getIntent().getStringExtra("profileImage");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        mobile=getIntent().getStringExtra("mobile");


        if (ContextCompat.checkSelfPermission(PartnerEditProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(PartnerEditProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(PartnerEditProfile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(APILinks.API_LINK)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        edtUserName = findViewById(R.id.edt_use_name);
        edtEmailAddress = findViewById(R.id.edt_email_address);
        edtMobile = findViewById(R.id.edt_mobile);
        ivProfile=findViewById(R.id.iv_profile);
      /*  edtPasswordOne = findViewById(R.id.edt_pass_one);
        edtPasswordTwo = findViewById(R.id.edt_pass_two);*/
        tvBtnSubmit = findViewById(R.id.btn_submit);
        tvBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtUserName.getText().toString().trim();
                email = edtEmailAddress.getText().toString().trim();
                mobile = edtMobile.getText().toString().trim();
               /* passwordOne = edtPasswordOne.getText().toString().trim();
                passwordTwo = edtPasswordTwo.getText().toString().trim();*/
                if (validateName()) {
                    if (isEmailValid()) {
                        if (validatePhone()) {
                           /* if (validatePassword()) {
                                //postSignUpValues();
                            }*/
                        }
                    }
                }
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PartnerEditProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PartnerEditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                } else {
                        selectedImageUrl = "";
                        Intent in = new Intent(PartnerEditProfile.this, NormalUserProfileImageBrowse.class);
                        in.putExtra("from","partner");
                        startActivityForResult(in, ID_GALLERY);
                }
            }
        });

        setValues();

    }

    private void setValues() {
        edtUserName.setText(name);
        edtEmailAddress.setText(email);
        edtMobile.setText(mobile);

        Glide.with(ivProfile.getContext()).load(currentProfileImage)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(ivProfile.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar)
                .into(ivProfile);
    }

    private boolean validateName() {
        if (name.equals("")) {
            Toast.makeText(PartnerEditProfile.this, "Enter name.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmailValid() {
        if (email.equals("")) {
            Toast.makeText(PartnerEditProfile.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(PartnerEditProfile.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validatePhone() {
        if (mobile.equals("")) {
            Toast.makeText(PartnerEditProfile.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length() != 10) {
                Toast.makeText(PartnerEditProfile.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

/*    private boolean validatePassword() {
        if (passwordOne.equals("")) {
            Toast.makeText(PartnerEditProfile.this, "Please enter minimum four digit password.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (passwordOne.length() < 4) {
                Toast.makeText(PartnerEditProfile.this, "Please enter minimum four digit password.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passwordOne.equals(passwordTwo)) {
                Toast.makeText(PartnerEditProfile.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID_GALLERY) {
            //selectedImageUrl = ImagePath.getPath(this, data.getData());
            if (!selectedImageUrl.equals("")) {
                try {
                    t1=new MyThread("First Thread");
                    t1.start();
                    dialogProgress.show();
                }catch (Exception e){
                    dialogProgress.dismiss();
                }
            }
        } else if (requestCode == ID_CROP) {
            if (!selectedFinalImageUrl.equals("")) {
                Glide.with(ivProfile.getContext()).load(selectedFinalImageUrl)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(ivProfile.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar)
                        .into(ivProfile);
            }
        }
    }

    public class MyThread extends Thread {
        String word;
        public MyThread(String rm) {
            word = rm;
        }
        public void run() {
            bt = BitmapFactory.decodeFile(selectedImageUrl);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
            selectedFinalImageUrl = "";
            Intent in = new Intent(PartnerEditProfile.this, ProfileImageCropActivity.class);
            in.putExtra("from","partner");
            startActivityForResult(in, ID_CROP);
            dialogProgress.dismiss();
            t1.interrupt();
        }
    }



}
