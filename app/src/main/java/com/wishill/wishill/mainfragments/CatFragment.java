package com.wishill.wishill.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeListActivity;
import com.wishill.wishill.adapter.FullCatListAdapter;
import com.wishill.wishill.api.recommendedColleges.fullCategories.FullCategoriesListAPI;
import com.wishill.wishill.api.recommendedColleges.fullCategories.FullCategoriesListData;
import com.wishill.wishill.api.recommendedColleges.fullCategories.FullCategoriesResponse;
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


public class CatFragment extends Fragment {
    View v;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    List<FullCategoriesListData> catList;
    FullCatListAdapter adapter;

    public CatFragment() {
        // Required empty public constructor
    }


    public static CatFragment newInstance(String param1, String param2) {
        CatFragment fragment = new CatFragment();
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
        v=inflater.inflate(R.layout.fragment_cat, container, false);
        dialogProgress = new DialogProgress(getActivity());

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
        linearLayoutManager= new GridLayoutManager(getActivity(), 3);
        rvList.setLayoutManager(linearLayoutManager);
        getList();


        return v;
    }
    private void getList() {
        retrofit.create(FullCategoriesListAPI.class).post("")
                .enqueue(new Callback<FullCategoriesResponse>() {
                    @Override
                    public void onResponse(Call<FullCategoriesResponse> call, Response<FullCategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            catList=response.body().getCatList();
                            adapter=new FullCatListAdapter(catList, getActivity(), new FullCatListAdapter.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(View v, int position) {
                                    Intent in=new Intent(getActivity(),CollegeListActivity.class);
                                    in.putExtra("subCatId",catList.get(position).getWishsubcat_id());
                                    in.putExtra("subCatName",catList.get(position).getWishsubcat_name());
                                    startActivity(in);
                                }
                            });
                            rvList.setAdapter(adapter);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<FullCategoriesResponse> call, Throwable t) {

                    }
                });
    }


}
