package com.wishill.wishill.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.CollegeListActivity;
import com.wishill.wishill.activity.SchoolDetails;
import com.wishill.wishill.activity.SchoolListActivity;
import com.wishill.wishill.activity.SocialMediaActivity;
import com.wishill.wishill.adapter.WishListListAdapter;
import com.wishill.wishill.api.recommendedColleges.mywishlist.MyWishListAPI;
import com.wishill.wishill.api.recommendedColleges.mywishlist.MyWishListData;
import com.wishill.wishill.api.recommendedColleges.mywishlist.MyWishListResponse;
import com.wishill.wishill.utilities.APILinks;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FavFragment extends Fragment {
    View v;
    SharedPreferences sharedPreferences;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    String userId;
    LinearLayout llLogout,llPartner;
    List<MyWishListData> dataList;
    WishListListAdapter wishListListAdapter;
    public FavFragment() {
        // Required empty public constructor
    }


    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
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
        v=inflater.inflate(R.layout.fragment_fav, container, false);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("wishill", getActivity().MODE_PRIVATE);
        userId=sharedPreferences.getString("userId","");
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
        rvList=v.findViewById(R.id.rv_list);
        tvNoItem=v.findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress=v.findViewById(R.id.progress);
        //linearLayoutManager= new GridLayoutManager(getActivity(), 2);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(linearLayoutManager);
        llLogout=v.findViewById(R.id.ll_logout);
        llPartner=v.findViewById(R.id.ll_partner);
        if(sharedPreferences.getString("login", "false").equals("true")){
            llLogout.setVisibility(View.GONE);
            rvList.setVisibility(View.VISIBLE);
            tvNoItem.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            getList();
        }else{
            llLogout.setVisibility(View.VISIBLE);
            rvList.setVisibility(View.GONE);
            tvNoItem.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        }
        llPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(),SocialMediaActivity.class);
                startActivity(in);
            }
        });
        return v;
    }


    private void getList() {
        retrofit.create(MyWishListAPI.class).post(userId)
                .enqueue(new Callback<MyWishListResponse>() {
                    @Override
                    public void onResponse(Call<MyWishListResponse> call, Response<MyWishListResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                dataList=response.body().getDataList();
                                if(dataList==null||dataList.size()==0){
                                    tvNoItem.setVisibility(View.VISIBLE);
                                }else{
                                    tvNoItem.setVisibility(View.GONE);
                                    wishListListAdapter=new WishListListAdapter(false, dataList, getActivity(), new WishListListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            if(dataList.get(position).getTypeID().equals("1")){
                                                Intent in=new Intent(getActivity(),CollegeDetailsActivity.class);
                                                in.putExtra("collegeID",dataList.get(position).getItemID());
                                                startActivity(in);
                                            }else if(dataList.get(position).getTypeID().equals("2")){
                                                Intent in=new Intent(getActivity(),SchoolDetails.class);
                                                in.putExtra("schoolID",dataList.get(position).getItemID());
                                                startActivity(in);
                                            }else if(dataList.get(position).getTypeID().equals("3")){

                                            }

                                        }

                                        @Override
                                        public void itemCall(View v, int position) {

                                        }

                                        @Override
                                        public void itemSendEnq(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(wishListListAdapter);
                                }


                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<MyWishListResponse> call, Throwable t) {

                    }
                });
    }



}
