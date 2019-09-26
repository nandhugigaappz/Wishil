package com.wishill.wishill.activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.TopRankingCollegeListAdapterSub;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowAPI;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowResponse;
import com.wishill.wishill.mainfragments.HomeFragment;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRankingActivity extends AppCompatActivity {
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    TopRankingCollegeListAdapterSub topRankingCollegeListAdapter;
    SharedPreferences sharedPreferences;
    String userId,userType;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    public Dialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        userType = sharedPreferences.getString("userType", "");
        dialogProgress = new DialogProgress(TopRankingActivity.this);

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
        linearLayoutManager = new LinearLayoutManager(TopRankingActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        topRankingCollegeListAdapter = new TopRankingCollegeListAdapterSub("sub", HomeFragment.topRankingCollegeList, TopRankingActivity.this, new TopRankingCollegeListAdapterSub.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent in = new Intent(TopRankingActivity.this, CollegeDetailsActivity.class);
                in.putExtra("collegeID", HomeFragment.topRankingCollegeList.get(position).getCollegeId());
                startActivity(in);
            }
            @Override
            public void followButton(View v, int position) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    collegeFollowAPI( HomeFragment.topRankingCollegeList.get(position).getCollegeId(),position);
                }else{
                    Intent toSlider = new Intent(TopRankingActivity.this, SocialMediaActivity.class);
                    startActivity(toSlider);
                }
            }
        });
        rvList.setAdapter(topRankingCollegeListAdapter);
    }

    private void collegeFollowAPI(String collegeId,final int position) {
        dialogProgress.show();
        retrofit.create(SendCollegeFollowAPI.class).post("1",
                collegeId,
                userId)
                .enqueue(new Callback<SendCollegeFollowResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeFollowResponse> call, Response<SendCollegeFollowResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            if(response.body().getSuccess()==1){
                                Toast.makeText(TopRankingActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                if(HomeFragment.topRankingCollegeList.get(position).getFollowStatus().equals("0")){
                                    HomeFragment.topRankingCollegeList.get(position).setFollowStatus("1");
                                }else{
                                    HomeFragment.topRankingCollegeList.get(position).setFollowStatus("0");
                                }
                                topRankingCollegeListAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(TopRankingActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TopRankingActivity.this,"Failed to follow",Toast.LENGTH_LONG).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SendCollegeFollowResponse> call, Throwable t) {
                        Toast.makeText(TopRankingActivity.this,"Failed to follow",Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();
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
