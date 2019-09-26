package com.wishill.wishill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.ExamListAdapter;
import com.wishill.wishill.api.recommendedColleges.examList.ExamListAPI;
import com.wishill.wishill.api.recommendedColleges.examList.ExamListData;
import com.wishill.wishill.api.recommendedColleges.examList.ExamResponse;
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

public class ExamListActivity extends AppCompatActivity {
    String examID;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    ExamListAdapter examListAdapter;
    List<ExamListData> examList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //examID=getIntent().getStringExtra("examID");
        examID=getIntent().getStringExtra("subCatId");

        dialogProgress = new DialogProgress(ExamListActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
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

        rvList=findViewById(R.id.rv_list);
        tvNoItem=findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        linearLayoutManager= new LinearLayoutManager(ExamListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

    }
    private void getList() {
        retrofit.create(ExamListAPI.class).post(examID)
                .enqueue(new Callback<ExamResponse>() {
                    @Override
                    public void onResponse(Call<ExamResponse> call, Response<ExamResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                examList=response.body().getCatList();
                                examListAdapter=new ExamListAdapter(examList, ExamListActivity.this, new ExamListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                        ExamListData examListData=examList.get(position);
                                        Intent in=new Intent(ExamListActivity.this,ExamDetailsPage.class);
                                        in.putExtra("examData",examListData);
                                        startActivity(in);
                                    }

                                });
                                rvList.setAdapter(examListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ExamResponse> call, Throwable t) {

                    }
                });
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
