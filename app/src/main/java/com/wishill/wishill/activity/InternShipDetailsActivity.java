package com.wishill.wishill.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getInternshipDetails.InternshipBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.getInternshipDetails.InternshipDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.getInternshipDetails.InternshipDetailsResponse;
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

public class InternShipDetailsActivity extends AppCompatActivity {
     String internshipID;
    DialogProgress dialogProgress;
    SharedPreferences sharedPreferences;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    TextView tvTitle;
    TextView tvLocation;
    TextView tvNumberOfOpens;
    TextView tvStartDate;
    TextView tvDuration;
    TextView tvStipend;
    TextView tvPerks;
    TextView tvPlacement;
    TextView tvSkills;
    TextView tvResponsibility;
    TextView tvWhoCan;
    TextView tvWebsite;
    ImageView ivCover;
    TextView tvAboutCompany;
    TextView tvAbout;

    ProgressBar progressBar;
    LinearLayout llMain;
    TextView tvToolBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_ship_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        internshipID=getIntent().getStringExtra("internshipID");
        dialogProgress = new DialogProgress(InternShipDetailsActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        tvToolBarTitle=findViewById(R.id.toolbar_title);

        tvTitle=findViewById(R.id.tv_title);
        tvLocation=findViewById(R.id.tv_location);
        tvNumberOfOpens=findViewById(R.id.tv_n_opens);
        tvStartDate=findViewById(R.id.tv_start_date);
        tvDuration=findViewById(R.id.tv_duration);
        tvStipend=findViewById(R.id.tv_stipend);
        tvPerks=findViewById(R.id.tv_perks);
        tvPlacement=findViewById(R.id.tv_placement);
        tvSkills=findViewById(R.id.tv_skills);
        tvResponsibility=findViewById(R.id.tv_responsibility);
        tvWhoCan=findViewById(R.id.tv_who_can);
        tvWebsite=findViewById(R.id.tv_website);
        ivCover=findViewById(R.id.iv_cover);
        tvAboutCompany=findViewById(R.id.tv_about_company);
        tvAbout=findViewById(R.id.tv_about);

        progressBar=findViewById(R.id.progress);
        llMain=findViewById(R.id.ll_main);
        llMain.setVisibility(View.GONE);


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
    }


    private void getDetails() {
        retrofit.create(InternshipDetailsAPI.class).post(internshipID)
                .enqueue(new Callback<InternshipDetailsResponse>() {
                    @Override
                    public void onResponse(Call<InternshipDetailsResponse> call, Response<InternshipDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            InternshipBasicDetailsData internShipData=response.body().getDetailsData().getInternshipBasicDetailsData();
                            setValues(internShipData);


                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<InternshipDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void setValues(InternshipBasicDetailsData internShipData) {
        llMain.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Glide.with(ivCover.getContext()).load(APILinks.IMAGE_LINK_INTERNSHIP+internShipData.getCoverImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        tvToolBarTitle.setText(internShipData.getTitle());
        tvTitle.setText(internShipData.getTitle());
        tvLocation.setText(internShipData.getCompanyName());

        tvSkills.setText(internShipData.getSkill());
        tvPerks.setText(internShipData.getPerks());
        tvStipend.setText(internShipData.getStipened());
        tvStartDate.setText(internShipData.getStartDate());
        tvNumberOfOpens.setText(internShipData.getNo_avialable());

        tvWhoCan.setText(Html.fromHtml(internShipData.getWhoapply()));
        tvWebsite.setText(internShipData.getWebsite());
        tvAboutCompany.setText(Html.fromHtml(internShipData.getCompanyDescription()));
        tvAbout.setText(Html.fromHtml(internShipData.getDescription()));
        tvDuration.setText(internShipData.getDuration());

       /* tvTitle.setText(internShipData.getPrimary_profile());
        tvLocation.setText(internShipData.getLocation());
        tvPartTime.setText(internShipData.getPart_time_allowed());
        tvNumberOfOpens.setText(internShipData.getNo_open());
        tvStartDate.setText(internShipData.getStatrt_date());
        tvDuration.setText(internShipData.getDuration());
        tvStipend.setText(internShipData.getStipend());
        tvPerks.setText(internShipData.getPerks());
        tvPlacement.setText(internShipData.getPre_placement());
        tvSkills.setText(internShipData.getSkills());
        tvResponsibility.setText(internShipData.getResponsibility());
        tvWhoCan.setText(internShipData.getW_can_apply());
        tvWebsite.setText(internShipData.getWebsite());*/
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
