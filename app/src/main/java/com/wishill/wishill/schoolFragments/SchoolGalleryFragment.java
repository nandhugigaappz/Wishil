package com.wishill.wishill.schoolFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
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
import com.wishill.wishill.activity.gallery.ImageSlideshowDialogFragment;
import com.wishill.wishill.adapter.SchoolGalleryListAdapter;
import com.wishill.wishill.api.recommendedColleges.getSchoolGallery.SchoolGalleryListAPI;
import com.wishill.wishill.api.recommendedColleges.getSchoolGallery.SchoolGalleryListData;
import com.wishill.wishill.api.recommendedColleges.getSchoolGallery.SchoolGalleryResponse;
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

public class SchoolGalleryFragment extends Fragment {
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
    public  static  List<SchoolGalleryListData> galleryList;
    SchoolGalleryListAdapter adapter;
    String ImagePath;

    public SchoolGalleryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SchoolGalleryFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    public static SchoolGalleryFragment newInstance(String param1, String param2) {
        SchoolGalleryFragment fragment = new SchoolGalleryFragment();
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
        v=inflater.inflate(R.layout.fragment_college_gallery, container, false);
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
        retrofit.create(SchoolGalleryListAPI.class).post(collegeID)
                .enqueue(new Callback<SchoolGalleryResponse>() {
                    @Override
                    public void onResponse(Call<SchoolGalleryResponse> call, Response<SchoolGalleryResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equals("1")){
                                progress.setVisibility(View.GONE);
                                galleryList=response.body().getCatList();
                                ImagePath=response.body().getImagePath();
                                adapter=new SchoolGalleryListAdapter(ImagePath,galleryList, getActivity(), new SchoolGalleryListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("position", position);
                                        bundle.putString("from", "school");
                                        bundle.putString("imagepath", ImagePath);
                                        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                                        ImageSlideshowDialogFragment newFragment = ImageSlideshowDialogFragment.newInstance();
                                        newFragment.setArguments(bundle);
                                        newFragment.show(ft, "slideshow");
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
                    public void onFailure(Call<SchoolGalleryResponse> call, Throwable t) {

                    }
                });
    }

}
