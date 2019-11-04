package com.wishill.wishill.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.wishill.wishill.adapter.ScholarshipListAdapter;
import com.wishill.wishill.api.recommendedColleges.ScholarshipList.ScholarshipListAPI;
import com.wishill.wishill.api.recommendedColleges.ScholarshipList.ScholarshipListData;
import com.wishill.wishill.api.recommendedColleges.ScholarshipList.ScholarshipResponse;
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

public class ScholarshipListActivity extends AppCompatActivity {
    String subCatId;
    String subCatName;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    List<ScholarshipListData> scholarshipList;
    ScholarshipListAdapter scholarshipListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subCatId=getIntent().getStringExtra("subCatId");
        subCatName=getIntent().getStringExtra("subCatName");
        dialogProgress = new DialogProgress(ScholarshipListActivity.this);
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
        linearLayoutManager= new LinearLayoutManager(ScholarshipListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
    }
    private void getList() {
        retrofit.create(ScholarshipListAPI.class).post(subCatId,"0")
                .enqueue(new Callback<ScholarshipResponse>() {
                    @Override
                    public void onResponse(Call<ScholarshipResponse> call, Response<ScholarshipResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                scholarshipList=response.body().getScholarshipList();
                                scholarshipListAdapter=new ScholarshipListAdapter(scholarshipList, ScholarshipListActivity.this, new ScholarshipListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                         Intent in =new Intent(ScholarshipListActivity.this,ScholarshipDetailsActivity.class);
                                         in.putExtra("scholarshipId",scholarshipList.get(position).getScholarshipId());
                                         startActivity(in);

                                    }
                                });
                                rvList.setAdapter(scholarshipListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<ScholarshipResponse> call, Throwable t) {

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
