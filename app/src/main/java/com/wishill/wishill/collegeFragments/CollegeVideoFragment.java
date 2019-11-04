package com.wishill.wishill.collegeFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.VideoViewActivity;
import com.wishill.wishill.adapter.CollegeVideoListAdapter;
import com.wishill.wishill.api.recommendedColleges.getCollegeVideo.CollegeVideoListAPI;
import com.wishill.wishill.api.recommendedColleges.getCollegeVideo.CollegeVideoListData;
import com.wishill.wishill.api.recommendedColleges.getCollegeVideo.CollegeVideoResponse;
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

public class CollegeVideoFragment extends Fragment {
    String collegeID;
    View v;
    TextView tvNoItem;
    ProgressBar progress;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    List<CollegeVideoListData> videoList;
    CollegeVideoListAdapter adapter;

    public CollegeVideoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CollegeVideoFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    // TODO: Rename and change types and number of parameters
    public static CollegeVideoFragment newInstance(String param1, String param2) {
        CollegeVideoFragment fragment = new CollegeVideoFragment();
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
        v=inflater.inflate(R.layout.fragment_college_notice, container, false);
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
        tvNoItem=v.findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress=v.findViewById(R.id.progress);
        linearLayoutManager= new GridLayoutManager(getActivity(), 2);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
        return v;
    }
    private void getList() {
        retrofit.create(CollegeVideoListAPI.class).post(collegeID)
                .enqueue(new Callback<CollegeVideoResponse>() {
                    @Override
                    public void onResponse(Call<CollegeVideoResponse> call, Response<CollegeVideoResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equals("1")){
                                videoList=response.body().getCatList();
                                if(videoList!=null&&videoList.size()!=0){
                                     progress.setVisibility(View.GONE);
                                    tvNoItem.setVisibility(View.GONE);
                                    adapter=new CollegeVideoListAdapter(videoList, getActivity(), new CollegeVideoListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Intent in=new Intent(getActivity(),VideoViewActivity.class);
                                            in.putExtra("videoUrl",videoList.get(position).getVideo());
                                            startActivity(in);
                                        }
                                    });
                                    rvList.setAdapter(adapter);
                                }else{
                                    progress.setVisibility(View.GONE);
                                    tvNoItem.setVisibility(View.VISIBLE);
                                }
                            }else{
                                progress.setVisibility(View.GONE);
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {
                            progress.setVisibility(View.GONE);
                            tvNoItem.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onFailure(Call<CollegeVideoResponse> call, Throwable t) {

                    }
                });
    }
}
