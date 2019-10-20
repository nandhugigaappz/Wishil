package com.wishill.wishill.studyAbroadFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.wishill.wishill.activity.NoticeDetailsActivity;
import com.wishill.wishill.adapter.InstituteNoticeAdapter;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryListData;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryResponse;
import com.wishill.wishill.api.recommendedColleges.getNoticeboardData.GetNoticeDataAPI;
import com.wishill.wishill.api.recommendedColleges.getNoticeboardData.NoticeData;
import com.wishill.wishill.api.recommendedColleges.getNoticeboardData.NoticeResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.BottomOffsetDecoration;
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

public class StudyAbroadNoticesFragment extends Fragment {
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
    List<NoticeData> noticeDataList;
    InstituteNoticeAdapter adapter;

    public StudyAbroadNoticesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public StudyAbroadNoticesFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    // TODO: Rename and change types and number of parameters
    public static StudyAbroadNoticesFragment newInstance(String param1, String param2) {
        StudyAbroadNoticesFragment fragment = new StudyAbroadNoticesFragment();
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
        linearLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
        return v;
    }
    private void getList() {
        retrofit.create(GetNoticeDataAPI.class).post(collegeID, "1")
                .enqueue(new Callback<NoticeResponse>() {
                    @Override
                    public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equals("1")){
                                noticeDataList = response.body().getCatList();
                                String imagePath=response.body().getImagePath();
                                if(noticeDataList!=null&&noticeDataList.size()!=0){
                                    progress.setVisibility(View.GONE);
                                    tvNoItem.setVisibility(View.GONE);
                                    adapter = new InstituteNoticeAdapter(getActivity(),imagePath, noticeDataList, new InstituteNoticeAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Intent in=new Intent(getActivity(), NoticeDetailsActivity.class);
                                            in.putExtra("noticeId",noticeDataList.get(position).getNoticeId());
                                            in.putExtra("from","1");
                                            startActivity(in);
                                        }
                                    });

                                    rvList.setAdapter(adapter);

                                    //to add space below recycler
                                    float offsetPx = 150;
                                    BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
                                    rvList.addItemDecoration(bottomOffsetDecoration);

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
                    public void onFailure(Call<NoticeResponse> call, Throwable t) {

                    }
                });
    }
}
