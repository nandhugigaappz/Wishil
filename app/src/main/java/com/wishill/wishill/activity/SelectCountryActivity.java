package com.wishill.wishill.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.wishill.wishill.adapter.CatListAdapter;
import com.wishill.wishill.api.recommendedColleges.categories.CategoriesListData;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListAPI;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListData;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectCountryActivity extends AppCompatActivity {

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView toolbarTitle;
    TextView tvNoItem;


    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    String catID;

    List<CountryListData> countryList;
//    List<String> countryNameList;
    List<CategoriesListData> catList;
    CatListAdapter catListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.toolbar_title);
        dialogProgress = new DialogProgress(SelectCountryActivity.this);
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
        linearLayoutManager= new GridLayoutManager(SelectCountryActivity.this, 2);
        rvList.setLayoutManager(linearLayoutManager);
        getCountryList();

    }

    private void setCountryList() {
        progress.setVisibility(View.GONE);
        if(countryList.size() > 0){
            tvNoItem.setVisibility(View.GONE);
            String imageUrl="";
            catListAdapter=new CatListAdapter(imageUrl, catList, SelectCountryActivity.this, new CatListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    Intent in=new Intent(SelectCountryActivity.this, SubCategoryActivity.class);
                    in.putExtra("catID","5");
                    in.putExtra("country", catList.get(position).getName());
                    in.putExtra("countryID", catList.get(position).getId());
                    startActivity(in);
                }
            });
            rvList.setAdapter(catListAdapter);
        }else{
            tvNoItem.setVisibility(View.VISIBLE);
        }
    }

    private void getCountryList() {
        retrofit.create(CountryListAPI.class).post("")
                .enqueue(new Callback<CountryListResponse>() {
                    @Override
                    public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                        if (response.isSuccessful()) {
                            countryList=response.body().getCatList();
//                            countryNameList = new ArrayList<>();
                            catList = new ArrayList<>();
                            for (int i = 0; i < countryList.size(); i++) {
//                                countryNameList.add(countryList.get(i).getCountry());
                                CategoriesListData data = new CategoriesListData();
                                data.setName(countryList.get(i).getCountry());
                                data.setId(countryList.get(i).getId());
                                data.setCategoryImg(countryList.get(i).getImage());
                                catList.add(data);
                            }

                            setCountryList();
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryListResponse> call, Throwable t) {

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
