package com.wishill.wishill.activity.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.enqdetails.EnqDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.enqdetails.EnqDetailsData;
import com.wishill.wishill.api.recommendedColleges.enqdetails.EnqDetailsResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.CircleTransform;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnqueryDetailsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String userId;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    String enqId;

    EnqDetailsData enqDetailsData;

    ImageView ivCover;
    TextView tvCollegeName;
    TextView tvDec;
    RelativeLayout llMain;
    ProgressBar progress;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvMobile;
    TextView  tvEmail;
    LinearLayout llUserDetails;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquery_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        dialogProgress=new DialogProgress(EnqueryDetailsActivity.this);
        enqId=getIntent().getStringExtra("enqId");
        userType = sharedPreferences.getString("userType", "");

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

        ivCover=findViewById(R.id.iv_cover);
        tvCollegeName=findViewById(R.id.tv_college_name);
        tvDec=findViewById(R.id.tv_des);
        llMain=findViewById(R.id.ll_main);
        llMain.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        ivProfileImage=findViewById(R.id.iv_profile_image);
        tvName=findViewById(R.id.tv_name);
        tvMobile=findViewById(R.id.tv_mobile);
        tvEmail=findViewById(R.id.tv_email);
        llUserDetails=findViewById(R.id.ll_user_details);

        getEnqDetails();


    }
    private void getEnqDetails() {
        retrofit.create(EnqDetailsAPI.class).post(enqId)
                .enqueue(new Callback<EnqDetailsResponse>() {
                    @Override
                    public void onResponse(Call<EnqDetailsResponse> call, Response<EnqDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            enqDetailsData=response.body().getEnqDetailsData();
                            setDetails();
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<EnqDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void setDetails() {
        if(userType.equals("normal")){
            llUserDetails.setVisibility(View.GONE);
        }else{
            llUserDetails.setVisibility(View.VISIBLE);
        }
        progress.setVisibility(View.GONE);
        llMain.setVisibility(View.VISIBLE);
        Glide.with(ivCover.getContext()).load(APILinks.IMAGE_LINK+enqDetailsData.getCollegeImg())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        tvCollegeName.setText(enqDetailsData.getItemName());
        tvDec.setText(enqDetailsData.getEnqContent());
        tvName.setText(enqDetailsData.getEnqUserName());
        tvMobile.setText(enqDetailsData.getEnqUserPhone());
        tvEmail.setText(enqDetailsData.getEnqUserEmail());
        Glide.with(ivProfileImage.getContext()).load(APILinks.IMAGE_LINK+enqDetailsData.getUserImage())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(ivProfileImage.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar)
                .into(ivProfileImage);
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
