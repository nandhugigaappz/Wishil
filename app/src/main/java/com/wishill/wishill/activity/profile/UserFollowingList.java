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
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.SchoolDetails;
import com.wishill.wishill.adapter.FollowingListAdapter;
import com.wishill.wishill.api.recommendedColleges.userfollowing.UserFollowingData;
import com.wishill.wishill.api.recommendedColleges.userfollowing.UserFollowingListAPI;
import com.wishill.wishill.api.recommendedColleges.userfollowing.UserFollowingResponse;
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

public class UserFollowingList extends AppCompatActivity {
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

    List<UserFollowingData> followingLIst;
    FollowingListAdapter followingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_following_list);
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
        //linearLayoutManager =new GridLayoutManager(UserFollowingList.this, 2);
        linearLayoutManager =new LinearLayoutManager(UserFollowingList.this);
        rvList.setLayoutManager(linearLayoutManager);
        dialogProgress = new DialogProgress(UserFollowingList.this);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);

    }


    private void getList() {
        retrofit.create(UserFollowingListAPI.class).post(userId)
                .enqueue(new Callback<UserFollowingResponse>() {
                    @Override
                    public void onResponse(Call<UserFollowingResponse> call, Response<UserFollowingResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                followingLIst = response.body().getDataList();
                                if (followingLIst == null || followingLIst.size() == 0) {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    rvList.setVisibility(View.GONE);
                                    tvToolbarTitle.setText("0 Following");
                                } else {
                                    tvToolbarTitle.setText(followingLIst.size() + " Following");
                                    tvNoItem.setVisibility(View.GONE);
                                    rvList.setVisibility(View.VISIBLE);
                                    followingListAdapter = new FollowingListAdapter(false, followingLIst, UserFollowingList.this, new FollowingListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            if(followingLIst.get(position).getTypeID().equals("1")){
                                                Intent in = new Intent(UserFollowingList.this, CollegeDetailsActivity.class);
                                                in.putExtra("collegeID", followingLIst.get(position).getItemID());
                                                startActivity(in);
                                            }else if(followingLIst.get(position).getTypeID().equals("2")){
                                                Intent in=new Intent(UserFollowingList.this,SchoolDetails.class);
                                                in.putExtra("schoolID",followingLIst.get(position).getItemID());
                                                startActivity(in);
                                            }

                                        }

                                        @Override
                                        public void itemCall(View v, int position) {

                                        }

                                        @Override
                                        public void itemSendEnq(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(followingListAdapter);
                                }

                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<UserFollowingResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
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
