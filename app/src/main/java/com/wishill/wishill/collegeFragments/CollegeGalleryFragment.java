package com.wishill.wishill.collegeFragments;

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
import com.wishill.wishill.adapter.CollegeGalleryListAdapter;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryListAPI;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryListData;
import com.wishill.wishill.api.recommendedColleges.getCollegeGallery.CollegeGalleryResponse;
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

public class CollegeGalleryFragment extends Fragment {
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
    public  static  List<CollegeGalleryListData> galleryList;
    CollegeGalleryListAdapter adapter;

    public CollegeGalleryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CollegeGalleryFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    // TODO: Rename and change types and number of parameters
    public static CollegeGalleryFragment newInstance(String param1, String param2) {
        CollegeGalleryFragment fragment = new CollegeGalleryFragment();
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
        retrofit.create(CollegeGalleryListAPI.class).post(collegeID)
                .enqueue(new Callback<CollegeGalleryResponse>() {
                    @Override
                    public void onResponse(Call<CollegeGalleryResponse> call, Response<CollegeGalleryResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equals("1")){
                                progress.setVisibility(View.GONE);
                                galleryList=response.body().getCatList();
                                if(galleryList!=null&&galleryList.size()!=0){
                                    final  String imagePath=response.body().getImagePath();
                                    adapter=new CollegeGalleryListAdapter(imagePath,galleryList, getActivity(), new CollegeGalleryListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("position", position);
                                            bundle.putString("from", "college");
                                            bundle.putString("imagepath", imagePath);
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
                    public void onFailure(Call<CollegeGalleryResponse> call, Throwable t) {

                    }
                });
    }

}
