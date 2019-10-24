package com.wishill.wishill.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.BlogDetails;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.InternShipListActivity;
import com.wishill.wishill.activity.RecommentationCollegeActivity;
import com.wishill.wishill.activity.ScholarshipDetailsActivity;
import com.wishill.wishill.activity.SelectCountryActivity;
import com.wishill.wishill.activity.StoriesListActivity;
import com.wishill.wishill.activity.SubCategoryActivity;
import com.wishill.wishill.activity.TopRankingActivity;
import com.wishill.wishill.activity.studyabroad.StudyAbrodCountries;
import com.wishill.wishill.adapter.AttractiveStudyTourListAdapter;
import com.wishill.wishill.adapter.BlogsListAdapter;
import com.wishill.wishill.adapter.HomeCatListAdapter;
import com.wishill.wishill.adapter.RecommendedCollegeListAdapter;
import com.wishill.wishill.adapter.StoriesListAdapter;
import com.wishill.wishill.adapter.TopRankingCollegeListAdapter;
import com.wishill.wishill.adapter.TrendingScholarshipsListAdapter;
import com.wishill.wishill.api.recommendedColleges.RecommendedCollegesListAPI;
import com.wishill.wishill.api.recommendedColleges.RecommendedCollegesListData;
import com.wishill.wishill.api.recommendedColleges.RecommendedCollegesResponse;
import com.wishill.wishill.api.recommendedColleges.attractiveStudyTour.AttractiveStudyTourListAPI;
import com.wishill.wishill.api.recommendedColleges.attractiveStudyTour.AttractiveStudyTourListData;
import com.wishill.wishill.api.recommendedColleges.attractiveStudyTour.AttractiveStudyTourResponse;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListData;
import com.wishill.wishill.api.recommendedColleges.storieslist.StoriesListResponse;
import com.wishill.wishill.api.recommendedColleges.toprankingColleges.TopRankingCollegesListAPI;
import com.wishill.wishill.api.recommendedColleges.toprankingColleges.TopRankingCollegesListData;
import com.wishill.wishill.api.recommendedColleges.toprankingColleges.TopRankingCollegesResponse;
import com.wishill.wishill.api.recommendedColleges.trendingScholerships.TrendingScholarshipsListAPI;
import com.wishill.wishill.api.recommendedColleges.trendingScholerships.TrendingScholarshipsListData;
import com.wishill.wishill.api.recommendedColleges.trendingScholerships.TrendingScholarshipsResponse;
import com.wishill.wishill.datamodel.HomeItemsModel;
import com.wishill.wishill.utilities.APILinks;

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

public class HomeFragment extends Fragment {

    List<HomeItemsModel> categories;
    RecyclerView rvCat;
    LinearLayoutManager linearLayoutManager;
    HomeCatListAdapter homeCatListAdapter;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    SharedPreferences sharedPreferences;
    String userId;

    public  static List<RecommendedCollegesListData> recommendedCollegeList;
    public  static List<TopRankingCollegesListData> topRankingCollegeList;
    public  static List<StoriesListData> blogsList;
    List<AttractiveStudyTourListData> attractiveStudyTourLIst;
    List<TrendingScholarshipsListData> trendingScholarshipsList;

    RecyclerView rvRecommendedCollege;
    LinearLayoutManager linearLayoutManagerRecommendedCollege;
    RecommendedCollegeListAdapter recommendedCollegeListAdapter;

    RecyclerView rvTopRankingColleges;
    LinearLayoutManager linearLayoutManagerTopRankingColleges;
    TopRankingCollegeListAdapter topRankingCollegeListAdapter;

    RecyclerView rvAttractiveStudyTour;
    LinearLayoutManager linearLayoutManagerAttractiveStudyTour;
    AttractiveStudyTourListAdapter attractiveStudyTourListAdapter;

    RecyclerView rvTrendingScholarships;
    LinearLayoutManager linearLayoutManagerTrendingScholarships;
    TrendingScholarshipsListAdapter trendingScholarshipsListAdapter;

    RecyclerView rvBlogs;
    LinearLayoutManager linearLayoutManagerBlogs;
    BlogsListAdapter blogsListAdapter;


    LinearLayout llExam;
    LinearLayout llRecommendation;
    LinearLayout llTopRanking;
    LinearLayout llInternship;
    LinearLayout llStories;
    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        rvCat=v.findViewById(R.id.rv_cat);
        linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCat.setLayoutManager(linearLayoutManager);
        setCategories();
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("wishill", getActivity().MODE_PRIVATE);
        userId = sharedPreferences.getString("rv_catuserId", "");

        llExam=v.findViewById(R.id.ll_exams);
        llRecommendation=v.findViewById(R.id.ll_recommendation);
        llTopRanking=v.findViewById(R.id.ll_top_ranking);
        llInternship=v.findViewById(R.id.ll_internship);
        llStories=v.findViewById(R.id.ll_stories);

        rvRecommendedCollege=v.findViewById(R.id.rv_recommended_college);
        linearLayoutManagerRecommendedCollege= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvRecommendedCollege.setLayoutManager(linearLayoutManagerRecommendedCollege);

        rvTopRankingColleges=v.findViewById(R.id.rv_top_ranking_college);
        linearLayoutManagerTopRankingColleges=new  LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTopRankingColleges.setLayoutManager(linearLayoutManagerTopRankingColleges);

        rvTrendingScholarships=v.findViewById(R.id.rv_trending_scholarships);
        linearLayoutManagerTrendingScholarships=new  LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTrendingScholarships.setLayoutManager(linearLayoutManagerTrendingScholarships);


        rvAttractiveStudyTour=v.findViewById(R.id.rv_attractive_study_tour);
        linearLayoutManagerAttractiveStudyTour=new  LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvAttractiveStudyTour.setLayoutManager(linearLayoutManagerAttractiveStudyTour);


        rvBlogs=v.findViewById(R.id.rv_blogs);
        linearLayoutManagerBlogs=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvBlogs.setLayoutManager(linearLayoutManagerBlogs);


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
        getRecommendedColleges();
        getTopRankingColleges();
        //getTrendingScholarships();
        getAttractiveStudyTour();
        getBlogs();



        homeCatListAdapter=new HomeCatListAdapter(categories, getActivity(), new HomeCatListAdapter.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {
                /*if (position == 0) {
                    //college
                    Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","1");
                    startActivity(in);
                } else if (position == 1) {
                    //school
                   *//* Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","4");
                    startActivity(in);*//*
                } else if (position == 2) {
                    //scholarship
                  *//*  Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","6");
                    startActivity(in);*//*
                } else if (position == 3) {
                    //study abroad
                    *//*Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","5");
                    startActivity(in);*//*
                 *//* Intent in=new Intent(getActivity(), SelectCountryActivity.class);
                    in.putExtra("catID","5");
                    startActivity(in);*//*

                    Intent in=new Intent(getActivity(), StudyAbrodCountries.class);
                    startActivity(in);
                }else  if(position==4) {
                    //study tour

                *//*    Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","8");
                    startActivity(in);*//*
                }else  if(position==5) {
                    //jobs

                    Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                    in.putExtra("catID","7");
                    startActivity(in);
                }*/
                Intent intent;
                switch (position) {
                    case 0:
                        //college
                        intent=new Intent(getActivity(), SubCategoryActivity.class);
                        intent.putExtra("catID","1");
                        startActivity(intent);
                        break;
                    case 1:
                        //study abroad
                        intent=new Intent(getActivity(), StudyAbrodCountries.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //jobs
                       /* intent = new Intent(getActivity(), SubCategoryActivity.class);
                        intent.putExtra("catID","7");
                        startActivity(intent);*/

                        Toast.makeText(getActivity(),"Coming Soon",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        //recommended
                        intent =new Intent(getActivity(), RecommentationCollegeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        rvCat.setAdapter(homeCatListAdapter);
        llExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Exam
              /*  Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                in.putExtra("catID","3");
                startActivity(in);*/

                Toast.makeText(getActivity(),"Coming Soon",Toast.LENGTH_LONG).show();
            }
        });
        llRecommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), RecommentationCollegeActivity.class);
                startActivity(in);
            }
        });
        llTopRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), TopRankingActivity.class);
                startActivity(in);
            }
        });
        llInternship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent in=new Intent(getActivity(), SubCategoryActivity.class);
                in.putExtra("catID","2");
                startActivity(in);*/
                Toast.makeText(getActivity(),"Coming Soon",Toast.LENGTH_LONG).show();
            }
        });
        llStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(),StoriesListActivity.class);
                startActivity(in);
            }
        });

        return v;
    }

    //recommended colleges
    private void getRecommendedColleges() {
        retrofit.create(RecommendedCollegesListAPI.class).getList("")
                .enqueue(new Callback<RecommendedCollegesResponse>() {
                    @Override
                    public void onResponse(Call<RecommendedCollegesResponse> call, Response<RecommendedCollegesResponse> response) {
                        if (response.isSuccessful()) {
                            recommendedCollegeList=response.body().getRecommendedCollegeList();
                            recommendedCollegeListAdapter=new RecommendedCollegeListAdapter("home",recommendedCollegeList, getActivity(), new RecommendedCollegeListAdapter.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(View v, int position) {
                                    Intent in=new Intent(getActivity(),CollegeDetailsActivity.class);
                                    in.putExtra("collegeID",recommendedCollegeList.get(position).getCollegeId());
                                    startActivity(in);
                                }
                            });
                            rvRecommendedCollege.setAdapter(recommendedCollegeListAdapter);

                            initAutoScroll();

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<RecommendedCollegesResponse> call, Throwable t) {

                    }
                });
    }

    //auto scrolling in recyclerView
    private void initAutoScroll() {
        final int speedScroll = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                count = linearLayoutManagerRecommendedCollege.findLastCompletelyVisibleItemPosition();
                if(count < recommendedCollegeListAdapter.getItemCount()){
                    if(count == recommendedCollegeListAdapter.getItemCount()-1){
                        flag = false;
                    }else {
                        flag = true;
                    }
                    if(flag) count++;
                    else count = 0;

                    rvRecommendedCollege.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }

    //top ranking universities
    private void getTopRankingColleges() {
        retrofit.create(TopRankingCollegesListAPI.class).getList(userId)
                .enqueue(new Callback<TopRankingCollegesResponse>() {
                    @Override
                    public void onResponse(Call<TopRankingCollegesResponse> call, Response<TopRankingCollegesResponse> response) {
                        if (response.isSuccessful()) {
                            topRankingCollegeList=response.body().getTopRankingCollegeList();
                            topRankingCollegeListAdapter=new TopRankingCollegeListAdapter("home",topRankingCollegeList, getActivity(), new TopRankingCollegeListAdapter.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(View v, int position) {
                                    Intent in=new Intent(getActivity(),CollegeDetailsActivity.class);
                                    in.putExtra("collegeID",topRankingCollegeList.get(position).getCollegeId());
                                    startActivity(in);
                                }
                            });
                            rvTopRankingColleges.setAdapter(topRankingCollegeListAdapter);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<TopRankingCollegesResponse> call, Throwable t) {

                    }
                });
    }
    //trending scholerships
    private void getTrendingScholarships() {
        retrofit.create(TrendingScholarshipsListAPI.class).getList("")
                .enqueue(new Callback<TrendingScholarshipsResponse>() {
                    @Override
                    public void onResponse(Call<TrendingScholarshipsResponse> call, Response<TrendingScholarshipsResponse> response) {
                        if (response.isSuccessful()) {
                            trendingScholarshipsList=response.body().getTrendingScholarshipsList();
                            trendingScholarshipsListAdapter=new TrendingScholarshipsListAdapter(trendingScholarshipsList, getActivity(), new TrendingScholarshipsListAdapter.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(View v, int position) {
                                    Intent in=new Intent(getActivity(), ScholarshipDetailsActivity.class);
                                    in.putExtra("scholarshipId",trendingScholarshipsList.get(position).getScholarshipid());
                                    startActivity(in);
                                }
                            });
                            rvTrendingScholarships.setAdapter(trendingScholarshipsListAdapter);

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<TrendingScholarshipsResponse> call, Throwable t) {

                    }
                });
    }

    //study tours
    private void getAttractiveStudyTour() {
        retrofit.create(AttractiveStudyTourListAPI.class).getList("")
                .enqueue(new Callback<AttractiveStudyTourResponse>() {
                    @Override
                    public void onResponse(Call<AttractiveStudyTourResponse> call, Response<AttractiveStudyTourResponse> response) {
                        if (response.isSuccessful()) {
                            attractiveStudyTourLIst=response.body().getAttractiveStudyTourLIst();
                            attractiveStudyTourListAdapter=new AttractiveStudyTourListAdapter(attractiveStudyTourLIst, getActivity(), new AttractiveStudyTourListAdapter.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(View v, int position) {

                                }
                            });
                            rvAttractiveStudyTour.setAdapter(attractiveStudyTourListAdapter);

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<AttractiveStudyTourResponse> call, Throwable t) {

                    }
                });
    }

    //stories

    private void getBlogs() {
        retrofit.create(StoriesListAPI.class).post("")
                .enqueue(new Callback<StoriesListResponse>() {
                    @Override
                    public void onResponse(Call<StoriesListResponse> call, Response<StoriesListResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus()==1){
                                blogsList=response.body().getDataList();
                                blogsListAdapter=new BlogsListAdapter(blogsList, getActivity(), new BlogsListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                        Intent in=new Intent(getActivity(), BlogDetails.class);
                                        in.putExtra("blogId",blogsList.get(position).getBlogId());
                                        startActivity(in);
                                    }
                                });
                                rvBlogs.setAdapter(blogsListAdapter);
                            }else{

                            }
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<StoriesListResponse> call, Throwable t) {

                    }
                });
    }

    private void setCategories() {
        categories = new ArrayList<>();
        categories.add(new HomeItemsModel("Colleges",R.drawable.college,"1"));
//        categories.add(new HomeItemsModel("Schools",R.drawable.schools,"0"));
//        categories.add(new HomeItemsModel("Scholarship",R.drawable.scholership,"0"));
        categories.add(new HomeItemsModel("Study Abroad",R.drawable.study_abroad,"1"));
//        categories.add(new HomeItemsModel("Study Tour",R.drawable.study_tour,"0"));
        categories.add(new HomeItemsModel("Jobs",R.drawable.jobs,"0"));
        categories.add(new HomeItemsModel("Recommended",R.drawable.scholership,"1"));

    }

}
