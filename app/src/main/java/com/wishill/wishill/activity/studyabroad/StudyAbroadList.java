package com.wishill.wishill.activity.studyabroad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.CollegeListActivity;
import com.wishill.wishill.activity.SocialMediaActivity;
import com.wishill.wishill.adapter.CollegeListAdapter;
import com.wishill.wishill.adapter.StudyAbroadListAdapter;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadListAPI;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadListData;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadResponse;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeListAPI;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import com.wishill.wishill.utilities.Variables;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudyAbroadList extends AppCompatActivity {
    String countryId;
    String subCatId;
    String userId;
    String userType;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    SharedPreferences sharedPreferences;
    List<StudyAbroadListData> studyAbroadList;
    List<StudyAbroadListData> studyAbroadListRemain;

    int count = 0;
    boolean loadingMain = true;
    StudyAbroadListAdapter  studyAbroadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_abroad_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");

        countryId=getIntent().getStringExtra("countryId");
        subCatId=getIntent().getStringExtra("subCatId");


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

        dialogProgress = new DialogProgress(StudyAbroadList.this);
        rvList = findViewById(R.id.rv_list);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        linearLayoutManager = new LinearLayoutManager(StudyAbroadList.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loadingMain) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadingMain = false;
                            getListRemain();
                        }
                    }
                }
            }
        });


    }

    private void getList() {
        retrofit.create(StudyAbroadListAPI.class).post(
                subCatId,
                countryId,
                count+"",
                "")
                .enqueue(new Callback<StudyAbroadResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadResponse> call, Response<StudyAbroadResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                tvNoItem.setVisibility(View.GONE);
                                studyAbroadList = response.body().getStudyAbroadList();
                                if(studyAbroadList==null||studyAbroadList.size()==0){
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    rvList.setVisibility(View.GONE);
                                }else{
                                    tvNoItem.setVisibility(View.GONE);
                                    rvList.setVisibility(View.VISIBLE);
                                    setValues();
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                                rvList.setVisibility(View.GONE);
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbroadResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
    private void getListRemain() {
        retrofit.create(StudyAbroadListAPI.class).post(
                subCatId,
                countryId,
                count+"",
                "")
                .enqueue(new Callback<StudyAbroadResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadResponse> call, Response<StudyAbroadResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            studyAbroadListRemain = response.body().getStudyAbroadList();
                            setValuesRemain();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbroadResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setValues() {
        count = 1;
        boolean loadMore = false;
        if (studyAbroadList.size() == 10) {
            loadMore = true;
            loadingMain = true;
        } else {
            loadMore = false;
            loadingMain = false;
        }
        studyAbroadListAdapter = new StudyAbroadListAdapter(loadMore, studyAbroadList, StudyAbroadList.this, new StudyAbroadListAdapter.ItemClickAdapterListener() {

            @Override
            public void itemClick(View v, int position) {
                   Intent in=new Intent(StudyAbroadList.this,StudyAbroadDetailsActivity.class);
                   in.putExtra("studyabroadID",studyAbroadList.get(position).getStudyabroadID());
                   startActivity(in);
            }
        });
        rvList.setAdapter(studyAbroadListAdapter);
    }


    private void setValuesRemain() {
        count = count + 1;
        boolean loadMore = false;
        int lastPosition = 0;
        if (studyAbroadListRemain == null || studyAbroadListRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {
            if (studyAbroadListRemain.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = studyAbroadList.size();
            for (int i = 0; i < studyAbroadListRemain.size(); i++) {
                studyAbroadList.add(studyAbroadListRemain.get(i));
            }
        }

        if (loadMore) {
            studyAbroadListAdapter.notifyDataSetChanged();
        } else {
            studyAbroadListAdapter = new StudyAbroadListAdapter(loadMore, studyAbroadList, StudyAbroadList.this, new StudyAbroadListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    Intent in=new Intent(StudyAbroadList.this,StudyAbroadDetailsActivity.class);
                    in.putExtra("studyabroadID",studyAbroadList.get(position).getStudyabroadID());
                    startActivity(in);
                }
            });
            rvList.setAdapter(studyAbroadListAdapter);
            linearLayoutManager.scrollToPosition(lastPosition);
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
