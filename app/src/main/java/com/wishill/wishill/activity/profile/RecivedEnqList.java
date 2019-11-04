package com.wishill.wishill.activity.profile;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.wishill.wishill.adapter.RecivedEnqListAdapter;
import com.wishill.wishill.api.recommendedColleges.recivedenqlist.RecivedEnqListAPI;
import com.wishill.wishill.api.recommendedColleges.recivedenqlist.RecivedEnqListData;
import com.wishill.wishill.api.recommendedColleges.recivedenqlist.RecivedEnqListResponse;
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

public class RecivedEnqList extends AppCompatActivity {
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;
    TextView tvToolbarTitle;
    SharedPreferences sharedPreferences;
    String userId;
    String institutionId;
    String institutionType;
    List<RecivedEnqListData> dataList;
    RecivedEnqListAdapter  recivedEnqListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recived_enq_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        tvToolbarTitle = findViewById(R.id.toolbar_title);
        institutionId=getIntent().getStringExtra("institutionId");
        institutionType=getIntent().getStringExtra("institutionType");

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

        rvList = findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(RecivedEnqList.this);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(RecivedEnqList.this);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        getList();

    }

    private void getList() {
        retrofit.create(RecivedEnqListAPI.class).post(userId)
                .enqueue(new Callback<RecivedEnqListResponse>() {
                    @Override
                    public void onResponse(Call<RecivedEnqListResponse> call, Response<RecivedEnqListResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                dataList = response.body().getDataList();
                                if (dataList == null || dataList.size() == 0) {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                } else {
                                    tvNoItem.setVisibility(View.GONE);
                                    recivedEnqListAdapter = new RecivedEnqListAdapter(false, dataList, RecivedEnqList.this, new RecivedEnqListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Intent in = new Intent(RecivedEnqList.this, EnqueryDetailsActivity.class);
                                            in.putExtra("enqId", dataList.get(position).getEnqID());
                                            startActivity(in);
                                        }

                                        @Override
                                        public void itemCall(View v, int position) {

                                        }

                                        @Override
                                        public void itemSendEnq(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(recivedEnqListAdapter);
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<RecivedEnqListResponse> call, Throwable t) {

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
