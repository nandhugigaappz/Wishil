package com.wishill.wishill.activity.studyabroad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.StudyAbroadCatListAdapter;
import com.wishill.wishill.api.recommendedColleges.studyabroadcategories.StudyAbroadCategoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.studyabroadcategories.StudyAbroadCategoriesListData;
import com.wishill.wishill.api.recommendedColleges.studyabroadcategories.StudyAbroadCategoriesResponse;
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

public class StudyAbroadCategories extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    String countryId;

    List<StudyAbroadCategoriesListData> catList;
    StudyAbroadCatListAdapter studyAbroadCatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_abroad_categories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(StudyAbroadCategories.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        countryId=getIntent().getStringExtra("countryId");

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
        linearLayoutManager= new GridLayoutManager(StudyAbroadCategories.this, 2);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

    }

    private void getList() {
        retrofit.create(StudyAbroadCategoriesListAPI.class).post(countryId)
                .enqueue(new Callback<StudyAbroadCategoriesResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadCategoriesResponse> call, Response<StudyAbroadCategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                catList=response.body().getCatList();
                                studyAbroadCatListAdapter=new StudyAbroadCatListAdapter(catList, StudyAbroadCategories.this, new StudyAbroadCatListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                       Intent in=new Intent(StudyAbroadCategories.this,StudyAbroadList.class);
                                       in.putExtra("countryId",countryId);
                                       in.putExtra("subCatId",catList.get(position).getSubcategoryID());
                                       startActivity(in);
                                    }
                                });
                                rvList.setAdapter(studyAbroadCatListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbroadCategoriesResponse> call, Throwable t) {

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
