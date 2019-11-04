package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.blogdetails.BlogDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.blogdetails.BlogDetailsData;
import com.wishill.wishill.api.recommendedColleges.blogdetails.BlogDetailsResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogDetails extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    ImageView ivCover;
    TextView tvHeading;
    TextView tvDes;
    String blogId;
    BlogDetailsData blogDetailsData;
    RelativeLayout llMain;
    ProgressBar progressBar;
    ImageView ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        dialogProgress=new DialogProgress(BlogDetails.this);
        blogId=getIntent().getStringExtra("blogId");


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
        tvHeading=findViewById(R.id.tv_heading);
        tvDes=findViewById(R.id.tv_des);
        llMain=findViewById(R.id.rl_main);
        llMain.setVisibility(View.GONE);
        progressBar=findViewById(R.id.progress);
        ivShare=findViewById(R.id.iv_share);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "Wishill App- Blog!!\n"+blogDetailsData.getTitle()+"\n"+APILinks.MAIN_URL+blogDetailsData.getDetailUrl();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "share"));
            }
        });

        getBlogDetails();

    }

    private void getBlogDetails() {
        retrofit.create(BlogDetailsAPI.class).post(blogId)
                .enqueue(new Callback<BlogDetailsResponse>() {
                    @Override
                    public void onResponse(Call<BlogDetailsResponse> call, Response<BlogDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            blogDetailsData=response.body().getEnqDetailsData();
                            setDetails();
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<BlogDetailsResponse> call, Throwable t) {

                    }
                });
    }


    private void setDetails() {
        progressBar.setVisibility(View.GONE);
        llMain.setVisibility(View.VISIBLE);
        Glide.with(ivCover.getContext()).load(APILinks.IMAGE_LINK+blogDetailsData.getCoverImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        tvHeading.setText(blogDetailsData.getTitle());
        tvDes.setText(android.text.Html.fromHtml(blogDetailsData.getContent()));
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
