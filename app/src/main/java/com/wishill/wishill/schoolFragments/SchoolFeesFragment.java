package com.wishill.wishill.schoolFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.SchoolFeesListAdapter;
import com.wishill.wishill.api.recommendedColleges.SchoolFess.SchoolFeesListAPI;
import com.wishill.wishill.api.recommendedColleges.SchoolFess.SchoolFeesListData;
import com.wishill.wishill.api.recommendedColleges.SchoolFess.SchoolFeesResponse;
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

public class SchoolFeesFragment extends Fragment {
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
    List<SchoolFeesListData> feesList;
    SchoolFeesListAdapter adapter;

    public SchoolFeesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SchoolFeesFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    // TODO: Rename and change types and number of parameters
    public static SchoolFeesFragment newInstance(String param1, String param2) {
        SchoolFeesFragment fragment = new SchoolFeesFragment();
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
        v=inflater.inflate(R.layout.fragment_school_fees, container, false);
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
        linearLayoutManager= new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(linearLayoutManager);
        //getList();
        return v;
    }
    private void getList() {
        retrofit.create(SchoolFeesListAPI.class).post(collegeID)
                .enqueue(new Callback<SchoolFeesResponse>() {
                    @Override
                    public void onResponse(Call<SchoolFeesResponse> call, Response<SchoolFeesResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus()==1){
                                progress.setVisibility(View.GONE);
                                feesList=response.body().getFeesList();
                                adapter=new SchoolFeesListAdapter(feesList, getActivity(), new SchoolFeesListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {

                                    }
                                });
                                rvList.setAdapter(adapter);
                            }else{
                                progress.setVisibility(View.GONE);
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<SchoolFeesResponse> call, Throwable t) {

                    }
                });
    }

}
