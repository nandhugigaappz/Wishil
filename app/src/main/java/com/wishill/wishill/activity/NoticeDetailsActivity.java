package com.wishill.wishill.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListResponse;
import com.wishill.wishill.api.recommendedColleges.getNoticeDetails.GetNoticeDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.getNoticeDetails.NoticeDetails;
import com.wishill.wishill.api.recommendedColleges.getNoticeDetails.NoticeDetailsResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticeDetailsActivity extends AppCompatActivity {

    ImageView noticeImage;
    TextView noticeTitle, noticeContent, errorText, dateOfNotice;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        String noticeId = getIntent().getStringExtra("noticeId");
        String from = getIntent().getStringExtra("from");

        dialogProgress = new DialogProgress(NoticeDetailsActivity.this);

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

        noticeImage = findViewById(R.id.notice_detail_image);
        noticeTitle = findViewById(R.id.notice_detail_title);
        noticeContent   = findViewById(R.id.notice_detail_content);
        errorText       = findViewById(R.id.notice_detail_no_content);
        dateOfNotice    = findViewById(R.id.date_text_notice_details);

        getNoticeDetails(noticeId, from);
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

    private void getNoticeDetails(final String noticeId, final String from) {
        dialogProgress.show();
        retrofit.create(GetNoticeDetailsAPI.class).post(noticeId, from)
                .enqueue(new Callback<NoticeDetailsResponse>() {
                    @Override
                    public void onResponse(Call<NoticeDetailsResponse> call, Response<NoticeDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String imagePath=response.body().getImagePath();
                            List<NoticeDetails> noticeDetails = response.body().getCatList();
                            if (noticeDetails != null && noticeDetails.size() > 0) {
                                NoticeDetails details = noticeDetails.get(0);
                                if (details.getNoticeImage() == null){
                                    noticeImage.setVisibility(View.GONE);
                                } else {
                                    Glide.with(NoticeDetailsActivity.this).load(APILinks.IMAGE_LINK + imagePath + details.getNoticeImage())
                                            .crossFade()
                                            .thumbnail(0.5f)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(noticeImage);
                                }
                                String title = details.getNoticeTitle().substring(0,1).toUpperCase().concat(details.getNoticeTitle().substring(1));
                                noticeTitle.setText(title);
                                String content = details.getNoticeContent().substring(0,1).toUpperCase().concat(details.getNoticeContent().substring(1));
                                noticeContent.setText(content);
                                dateOfNotice.setText(details.getDateOfIssue());
                            } else {
                                errorText.setVisibility(View.VISIBLE);
                            }
                        } else {
                            dialogProgress.dismiss();
                            errorText.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<NoticeDetailsResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
}
