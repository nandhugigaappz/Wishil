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
import com.wishill.wishill.adapter.SendEnqListAdapter;
import com.wishill.wishill.api.recommendedColleges.sendenqlist.SendEnqListAPI;
import com.wishill.wishill.api.recommendedColleges.sendenqlist.SendEnqListData;
import com.wishill.wishill.api.recommendedColleges.sendenqlist.SendEnqListResponse;
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

public class SendEnqListActivity extends AppCompatActivity {
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

    List<SendEnqListData> sendEnqList;
    SendEnqListAdapter sendEnqListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_enq_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        tvToolbarTitle = findViewById(R.id.toolbar_title);
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
        linearLayoutManager = new LinearLayoutManager(SendEnqListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(SendEnqListActivity.this);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList() {
        retrofit.create(SendEnqListAPI.class).post(userId)
                .enqueue(new Callback<SendEnqListResponse>() {
                    @Override
                    public void onResponse(Call<SendEnqListResponse> call, Response<SendEnqListResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);

                            if (response.body().getStatus() == 1) {
                                sendEnqList = response.body().getDataList();
                                if (sendEnqList == null || sendEnqList.size() == 0) {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                } else {
                                    tvNoItem.setVisibility(View.GONE);
                                    sendEnqListAdapter = new SendEnqListAdapter(false, sendEnqList, SendEnqListActivity.this, new SendEnqListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Intent in = new Intent(SendEnqListActivity.this, EnqueryDetailsActivity.class);
                                            in.putExtra("enqId", sendEnqList.get(position).getEnqID());
                                            startActivity(in);
                                        }

                                        @Override
                                        public void itemCall(View v, int position) {

                                        }

                                        @Override
                                        public void itemSendEnq(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(sendEnqListAdapter);
                                }

                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<SendEnqListResponse> call, Throwable t) {

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
