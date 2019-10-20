package com.wishill.wishill.studyAbroadFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.wishill.wishill.activity.VideoViewActivity;
import com.wishill.wishill.activity.gallery.GalleryPreviewFragment;
import com.wishill.wishill.adapter.GalleryListAdapter;
import com.wishill.wishill.api.recommendedColleges.getGallery.GalleryListAPI;
import com.wishill.wishill.api.recommendedColleges.getGallery.GalleryListData;
import com.wishill.wishill.api.recommendedColleges.getGallery.GalleryResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.BottomOffsetDecoration;
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

public class StudyAbroadGalleryFragment extends Fragment {
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
    public  static  List<GalleryListData> galleryList;
    GalleryListAdapter adapter;

    String video = "1";

    public StudyAbroadGalleryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public StudyAbroadGalleryFragment(String collegeID) {
        this.collegeID=collegeID;
    }


    // TODO: Rename and change types and number of parameters
    public static StudyAbroadGalleryFragment newInstance(String param1, String param2) {
        StudyAbroadGalleryFragment fragment = new StudyAbroadGalleryFragment();
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
        retrofit.create(GalleryListAPI.class).post(collegeID, "1")
                .enqueue(new Callback<GalleryResponse>() {
                    @Override
                    public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getStatus().equals("1")){
                                progress.setVisibility(View.GONE);
                                galleryList=response.body().getCatList();
                                if(galleryList!=null&&galleryList.size()!=0){
                                    final  String imagePath=response.body().getImagePath();
                                    adapter=new GalleryListAdapter(imagePath,galleryList, new GalleryListAdapter.ItemClickAdapterListener() {
                                        @Override
                                        public void itemClick(View v, int position) {
                                            if (galleryList.get(position).getCategoryID().equals(video)){
                                                Intent in=new Intent(getActivity(), VideoViewActivity.class);
                                                in.putExtra("videoUrl",galleryList.get(position).getVideo());
                                                startActivity(in);
                                            } else {
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("position", position);
                                                bundle.putString("from", "abroad");
                                                bundle.putString("imagepath", imagePath);
                                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                GalleryPreviewFragment newFragment = GalleryPreviewFragment.newInstance();
                                                newFragment.setArguments(bundle);
                                                newFragment.show(ft, "slideshow");
                                            }
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
                    public void onFailure(Call<GalleryResponse> call, Throwable t) {

                    }
                });
    }

}
