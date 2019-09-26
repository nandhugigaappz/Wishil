package com.wishill.wishill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.wishill.wishill.api.recommendedColleges.categories.CategoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.categories.CategoriesListData;
import com.wishill.wishill.api.recommendedColleges.categories.CategoriesResponse;
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

public class SubCategoryActivity extends AppCompatActivity {
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
    List<CategoriesListData> catList;
    CatListAdapter catListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.toolbar_title);
        dialogProgress = new DialogProgress(SubCategoryActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        catID=getIntent().getStringExtra("catID");
        if(catID.equals("1")){
           toolbarTitle.setText("College Categories");
        }else if(catID.equals("4")){
            toolbarTitle.setText("School Categories");
        }else if(catID.equals("6")){
            toolbarTitle.setText("Scholarship Categories");
        }else if(catID.equals("5")){
            toolbarTitle.setText("Study Abroad Categories");
        }else if(catID.equals("8")){
            toolbarTitle.setText("Study Tour Categories");
        }else if(catID.equals("7")){
            toolbarTitle.setText("Jobs Categories");
        }else if(catID.equals("3")){
            toolbarTitle.setText("Exams Categories");
        }else if(catID.equals("2")){
            toolbarTitle.setText("Internship Categories");
        }
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
        linearLayoutManager= new GridLayoutManager(SubCategoryActivity.this, 2);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

    }
    private void getList() {
        retrofit.create(CategoriesListAPI.class).post(catID)
                .enqueue(new Callback<CategoriesResponse>() {
                    @Override
                    public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                catList=response.body().getCatList();
                                String imageUrl=response.body().getImageUrl();
                                catListAdapter=new CatListAdapter(imageUrl,catList, SubCategoryActivity.this, new CatListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                        if(catID.equals("1")){
                                            //college
                                            Intent in=new Intent(SubCategoryActivity.this,CollegeListActivity.class);
                                            in.putExtra("subCatId",catList.get(position).getId());
                                            in.putExtra("subCatName",catList.get(position).getName());
                                            in.putExtra("catID",catID);
                                            startActivity(in);
                                        }else if(catID.equals("4")){
                                            //school
                                           Intent in=new Intent(SubCategoryActivity.this,SchoolListActivity.class);
                                           in.putExtra("subCatId",catList.get(position).getId());
                                           in.putExtra("subCatName",catList.get(position).getName());
                                           in.putExtra("catID",catID);
                                           startActivity(in);
                                        }else if(catID.equals("6")){
                                            //scholarship ships
                                            Intent in=new Intent(SubCategoryActivity.this,ScholarshipListActivity.class);
                                            in.putExtra("subCatId",catList.get(position).getId());
                                            in.putExtra("subCatName",catList.get(position).getName());
                                            startActivity(in);
                                        }else if(catID.equals("5")){
                                            //study abroad
                                        }else if(catID.equals("8")){
                                            //study tour
                                            Intent in=new Intent(SubCategoryActivity.this,StudyTourList.class);
                                            in.putExtra("subCatId",catList.get(position).getId());
                                            startActivity(in);
                                        }else if(catID.equals("7")){
                                            //jobs

                                        }else if(catID.equals("3")){
                                            //exam
                                            Intent in=new Intent(SubCategoryActivity.this,ExamListActivity.class);
                                            in.putExtra("subCatId",catList.get(position).getId());
                                            startActivity(in);
                                        }else if(catID.equals("2")) {
                                            Intent in = new Intent(SubCategoryActivity.this, InternShipListActivity.class);
                                            in.putExtra("subCatId",catList.get(position).getId());
                                            in.putExtra("heading",catList.get(position).getName());
                                            startActivity(in);
                                        }
                                    }
                                });
                                rvList.setAdapter(catListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesResponse> call, Throwable t) {

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
