package com.wishill.wishill.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getScholarshipDetails.ScholarshipBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.getScholarshipDetails.ScholarshipDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.getScholarshipDetails.ScholarshipDetailsResponse;
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

public class ScholarshipDetailsActivity extends AppCompatActivity {
     String scholarshipId;
    DialogProgress dialogProgress;
    ImageView ivLogo;

    TextView tvTitle;
    TextView tvLocation;
    TextView tvMobile;
    TextView tvEmail;
    TextView tvWebsite;
    TextView tvDec;
    ProgressBar progress;
    ScrollView scrollView;

    TextView tvCat;
    TextView tvProvider;
    TextView tvAward;
    TextView tvType;
    TextView tvSubType;
    TextView tvScheme;
    TextView tvOpen;
    TextView tvClosed;
    TextView tvAddress;
    TextView tvZipCode;
    TextView tvWhoCan;



    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    public Dialog alertDialog;
    SharedPreferences sharedPreferences;
    String userEmail;
    RelativeLayout rlApply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userEmail=sharedPreferences.getString("userEmail", "");
        scholarshipId=getIntent().getStringExtra("scholarshipId");
        dialogProgress = new DialogProgress(ScholarshipDetailsActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        tvTitle=findViewById(R.id.tv_title);
        tvLocation=findViewById(R.id.tv_location);
        tvMobile=findViewById(R.id.tv_mobile);
        tvEmail=findViewById(R.id.tv_email);
        tvWebsite=findViewById(R.id.tv_website);
        tvDec=findViewById(R.id.tv_des);
        progress=findViewById(R.id.progress);
        scrollView=findViewById(R.id.scroll_view);
        scrollView.setVisibility(View.GONE);
        rlApply=findViewById(R.id.rl_apply);

        tvCat=findViewById(R.id.tv_cat);
        tvProvider=findViewById(R.id.tv_provider);
        tvAward=findViewById(R.id.tv_award);
        tvType=findViewById(R.id.tv_type);
        tvSubType=findViewById(R.id.tv_sub_type);
        tvScheme=findViewById(R.id.tv_scheme);
        tvOpen=findViewById(R.id.tv_open);
        tvClosed=findViewById(R.id.tv_closed);
        tvAddress=findViewById(R.id.tv_address);
        tvZipCode=findViewById(R.id.tv_zip_code);

        ivLogo=findViewById(R.id.iv_logo);
        tvWhoCan=findViewById(R.id.tv_who_can);

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
        getDetails();
        rlApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScholarshipDetailsActivity.this,"Pending",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetails() {
        retrofit.create(ScholarshipDetailsAPI.class).post(scholarshipId)
                .enqueue(new Callback<ScholarshipDetailsResponse>() {
                    @Override
                    public void onResponse(Call<ScholarshipDetailsResponse> call, Response<ScholarshipDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                progress.setVisibility(View.GONE);
                                ScholarshipBasicDetailsData scholarshipBasicDetailsData=response.body().getDetails().getBasicDetails();
                                setValues(scholarshipBasicDetailsData);

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ScholarshipDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void setValues(ScholarshipBasicDetailsData scholarshipBasicDetailsData) {
        Glide.with(ivLogo.getContext()).load(APILinks.IMAGE_LINK_SCHOLERSHIP +scholarshipBasicDetailsData.getCoverImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivLogo);


        tvTitle.setText(scholarshipBasicDetailsData.getTitle());
        tvLocation.setText(scholarshipBasicDetailsData.getWishsubcat_name());
        tvCat.setText(scholarshipBasicDetailsData.getWishsubcat_name());
         tvDec.setText(scholarshipBasicDetailsData.getDescription());
        tvWhoCan.setText(Html.fromHtml(scholarshipBasicDetailsData.getWhoapply()));
        tvAward.setText(scholarshipBasicDetailsData.getAward());
        tvOpen.setText(scholarshipBasicDetailsData.getOpen_on());
        tvClosed.setText(scholarshipBasicDetailsData.getClosed());
        tvMobile.setText(scholarshipBasicDetailsData.getTel());
        tvEmail.setText(scholarshipBasicDetailsData.getEmail());
        tvWebsite.setText(scholarshipBasicDetailsData.getWebsite());


  /*      tvTitle.setText(scholarshipDetailsData.getTitle());
        tvLocation.setText(scholarshipDetailsData.getLocation()+", "+scholarshipDetailsData.getCountry());

        tvAward.setText(scholarshipDetailsData.getAward());
         tvCat.setText(scholarshipDetailsData.getCategory());
         tvProvider.setText(scholarshipDetailsData.getProvider());
         tvType.setText(scholarshipDetailsData.getType());
         tvSubType.setText(scholarshipDetailsData.getSubType());
        tvScheme.setText(scholarshipDetailsData.getScheme());

        tvOpen.setText(scholarshipDetailsData.getOpen());
        tvClosed.setText(scholarshipDetailsData.getClosed());

        tvAddress.setText(scholarshipDetailsData.getApartment()+", "+scholarshipDetailsData.getStreet()+", "+
        scholarshipDetailsData.getLocation());
        tvZipCode.setText(scholarshipDetailsData.getZip());

        tvDec.setText(scholarshipDetailsData.getDescription());
        tvMobile.setText(scholarshipDetailsData.getTel());
        tvEmail.setText(scholarshipDetailsData.getEmail());
        tvWebsite.setText(scholarshipDetailsData.getWebsite());*/
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
