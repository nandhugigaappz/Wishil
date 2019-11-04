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
import com.wishill.wishill.adapter.StudyAbrodCountryAdapter;
import com.wishill.wishill.api.recommendedColleges.studyabrodcountires.StudyAbrodCountiresListAPI;
import com.wishill.wishill.api.recommendedColleges.studyabrodcountires.StudyAbrodCountiresListData;
import com.wishill.wishill.api.recommendedColleges.studyabrodcountires.StudyAbrodCountiresResponse;
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

public class StudyAbrodCountries extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    List<StudyAbrodCountiresListData> catList;
    StudyAbrodCountryAdapter studyAbrodCountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_abrod_countries);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(StudyAbrodCountries.this);
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
        linearLayoutManager= new GridLayoutManager(StudyAbrodCountries.this, 2);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

    }

    private void getList() {
        retrofit.create(StudyAbrodCountiresListAPI.class).post("")
                .enqueue(new Callback<StudyAbrodCountiresResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbrodCountiresResponse> call, Response<StudyAbrodCountiresResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                catList=response.body().getCatList();
                                studyAbrodCountryAdapter=new StudyAbrodCountryAdapter(catList, StudyAbrodCountries.this, new StudyAbrodCountryAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                         Intent in=new Intent(StudyAbrodCountries.this,StudyAbroadCategories.class);
                                         in.putExtra("countryId",catList.get(position).getCountryID());
                                         startActivity(in);
                                    }
                                });
                                rvList.setAdapter(studyAbrodCountryAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbrodCountiresResponse> call, Throwable t) {

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
