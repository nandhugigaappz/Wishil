package com.wishill.wishill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.wishill.wishill.adapter.ExamCatListAdapter;
import com.wishill.wishill.api.recommendedColleges.examCategories.ExamCategoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.examCategories.ExamCategoriesListData;
import com.wishill.wishill.api.recommendedColleges.examCategories.ExamCategoriesResponse;
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

public class ExamCategoryActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView toolbarTitle;
    TextView tvNoItem;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    ExamCatListAdapter  examCatListAdapter;
    List<ExamCategoriesListData> catList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.toolbar_title);
        dialogProgress = new DialogProgress(ExamCategoryActivity.this);
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
        progress=findViewById(R.id.progress);
        tvNoItem=findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        linearLayoutManager= new GridLayoutManager(ExamCategoryActivity.this, 2);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

    }
    private void getList() {
        retrofit.create(ExamCategoriesListAPI.class).post("16")
                .enqueue(new Callback<ExamCategoriesResponse>() {
                    @Override
                    public void onResponse(Call<ExamCategoriesResponse> call, Response<ExamCategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                catList=response.body().getCatList();
                                examCatListAdapter=new ExamCatListAdapter(catList, ExamCategoryActivity.this, new ExamCatListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                         String examID=catList.get(position).getExamID();
                                         Intent in=new Intent(ExamCategoryActivity.this,ExamListActivity.class);
                                         in.putExtra("examID",examID);
                                         startActivity(in);

                                    }
                                });
                                rvList.setAdapter(examCatListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ExamCategoriesResponse> call, Throwable t) {

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
