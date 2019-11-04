package com.wishill.wishill.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.SocialMediaActivity;
import com.wishill.wishill.adapter.NotificationListAdapter;
import com.wishill.wishill.api.recommendedColleges.getNotifications.NotificationListAPI;
import com.wishill.wishill.api.recommendedColleges.getNotifications.NotificationListData;
import com.wishill.wishill.api.recommendedColleges.getNotifications.NotificationResponse;
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


public class NotificationFragment extends Fragment {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    View v;
    String userId;
    NotificationListAdapter notificationListAdapter;
    SharedPreferences sharedPreferences;
    List<NotificationListData> notificationList;

    LinearLayout llLogout,llPartner;
    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        v=inflater.inflate(R.layout.fragment_notification, container, false);
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
        retrofit.create(NotificationListAPI.class).post(userId)
                .enqueue(new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus().equals("1")){
                                tvNoItem.setVisibility(View.GONE);
                                notificationList=response.body().getCatList();
                                if(notificationList==null||notificationList.size()==0){
                                    tvNoItem.setVisibility(View.VISIBLE);
                                }else{
                                    notificationListAdapter=new NotificationListAdapter(notificationList, getActivity(), new NotificationListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {

                                        }
                                    });
                                    rvList.setAdapter(notificationListAdapter);
                                }

                            }else{

                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationResponse> call, Throwable t) {

                    }
                });
    }


}
