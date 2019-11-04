package com.wishill.wishill.collegeFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.wishill.wishill.adapter.CollegeCourseListAdapter;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeCoursesDetailsData;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollegeContactFragment extends Fragment {
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
    List<CollegeCoursesDetailsData> courseList;
    CollegeCourseListAdapter adapter;
    public CollegeContactFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CollegeContactFragment(String collegeID,List<CollegeCoursesDetailsData> courseList) {
        this.collegeID=collegeID;
        this.courseList=courseList;

    }


    // TODO: Rename and change types and number of parameters
    public static CollegeContactFragment newInstance(String param1, String param2) {
        CollegeContactFragment fragment = new CollegeContactFragment();
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
        v=inflater.inflate(R.layout.fragment_college_contact, container, false);
        setValues();
        return v;
    }

    private void setValues() {
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
        if(courseList==null||courseList.size()==0){
            tvNoItem.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }else{
            tvNoItem.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
            adapter=new CollegeCourseListAdapter(courseList, getActivity(), new CollegeCourseListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {

                }
            });
            rvList.setAdapter(adapter);
        }
    }

}
