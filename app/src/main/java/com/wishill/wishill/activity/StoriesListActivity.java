package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.StoriesListAdapter;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListData;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListResponse;
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

public class StoriesListActivity extends AppCompatActivity {
    RecyclerView rvList;
    //LinearLayoutManager linearLayoutManager;
    RecyclerView.LayoutManager linearLayoutManager;
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

    List<StoriesListData> dataList;
    StoriesListAdapter storiesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        //linearLayoutManager =new GridLayoutManager(StoriesListActivity.this, 2);
        linearLayoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(StoriesListActivity.this);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        getList();

    }

    private void getList() {
        retrofit.create(StoriesListAPI.class).post("")
                .enqueue(new Callback<StoriesListResponse>() {
                    @Override
                    public void onResponse(Call<StoriesListResponse> call, Response<StoriesListResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                dataList = response.body().getDataList();
                                if (dataList == null || dataList.size() == 0) {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    rvList.setVisibility(View.GONE);
                                    tvToolbarTitle.setText("0 Following");
                                } else {
                                    tvNoItem.setVisibility(View.GONE);
                                    rvList.setVisibility(View.VISIBLE);
                                    storiesListAdapter=new StoriesListAdapter(false, dataList, StoriesListActivity.this, new StoriesListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Intent in=new Intent(StoriesListActivity.this, BlogDetails.class);
                                            in.putExtra("blogId",dataList.get(position).getBlogId());
                                            startActivity(in);
                                        }
                                    });
                                    rvList.setAdapter(storiesListAdapter);
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<StoriesListResponse> call, Throwable t) {

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
