package com.wishill.wishill.activity;

import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.profile.SendEnqListActivity;
import com.wishill.wishill.adapter.MyFollowersListAdapter;
import com.wishill.wishill.adapter.SendEnqListAdapter;
import com.wishill.wishill.api.recommendedColleges.myfollowerslist.MyFollowersListAPI;
import com.wishill.wishill.api.recommendedColleges.myfollowerslist.MyFollowersListData;
import com.wishill.wishill.api.recommendedColleges.myfollowerslist.MyFollowersListResponse;
import com.wishill.wishill.api.recommendedColleges.sendenqlist.SendEnqListAPI;
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

public class MyFollowersList extends AppCompatActivity {
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
    String itemId;
    String itemType;
    List<MyFollowersListData> dataList;
    String profileImagePath;
    MyFollowersListAdapter myFollowersListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_followers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemId=getIntent().getStringExtra("institutionId");
        itemType=getIntent().getStringExtra("institutionType");

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
        linearLayoutManager = new LinearLayoutManager(MyFollowersList.this);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(MyFollowersList.this);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        getList();

    }
    private void getList() {
        retrofit.create(MyFollowersListAPI.class).post(itemType,itemId)
                .enqueue(new Callback<MyFollowersListResponse>() {
                    @Override
                    public void onResponse(Call<MyFollowersListResponse> call, Response<MyFollowersListResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                dataList=response.body().getDataList();
                                profileImagePath=response.body().getProfileUrl();
                                if(dataList==null||dataList.size()==0){
                                    tvNoItem.setVisibility(View.VISIBLE);
                                }else{
                                    myFollowersListAdapter=new MyFollowersListAdapter(profileImagePath,false, dataList, MyFollowersList.this, new MyFollowersListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(myFollowersListAdapter);
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<MyFollowersListResponse> call, Throwable t) {

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
