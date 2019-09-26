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
import com.wishill.wishill.adapter.IntershipListAdapter;
import com.wishill.wishill.api.recommendedColleges.internshipList.InternShipData;
import com.wishill.wishill.api.recommendedColleges.internshipList.InternShipListAPI;
import com.wishill.wishill.api.recommendedColleges.internshipList.InternShipResponse;
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

public class InternShipListActivity extends AppCompatActivity {
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;
    List<InternShipData> list;
    IntershipListAdapter intershipListAdapter;
    String subCatId;
    String catName;
    TextView tvToolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_ship_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        subCatId=getIntent().getStringExtra("subCatId");
        catName=getIntent().getStringExtra("heading");
        rvList=findViewById(R.id.rv_list);
        linearLayoutManager= new LinearLayoutManager(InternShipListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(InternShipListActivity.this);
        tvNoItem=findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        tvToolbarTitle=findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(catName);

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

        getList();
    }

    private void getList() {
        retrofit.create(InternShipListAPI.class).getList(subCatId,"0")
                .enqueue(new Callback<InternShipResponse>() {
                    @Override
                    public void onResponse(Call<InternShipResponse> call, Response<InternShipResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                list=response.body().getCatList();
                                intershipListAdapter=new IntershipListAdapter(list, InternShipListActivity.this, new IntershipListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                       Intent in=new Intent(InternShipListActivity.this,InternShipDetailsActivity.class);
                                       in.putExtra("internshipID",list.get(position).getInternshipID());
                                       startActivity(in);
                                    }
                                });
                                rvList.setAdapter(intershipListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvNoItem.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<InternShipResponse> call, Throwable t) {
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
