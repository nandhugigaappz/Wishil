package com.wishill.wishill.studyAbroadFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wishill.wishill.R;
import com.wishill.wishill.adapter.IndividualDashboardListAdapter;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeAmenitiesDetailsData;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadAmenitiesDetailsData;
import com.wishill.wishill.model.IndividulDashboardModel;

import java.util.ArrayList;
import java.util.List;

public class StudyAbroadFacilitiesFragment extends Fragment {

    RecyclerView rvDashboard;
    LinearLayoutManager linearLayoutManager;
    List<IndividulDashboardModel> categories;
    View v;
    List<StudyAbroadAmenitiesDetailsData> amenitiesList;
    IndividualDashboardListAdapter individualDashboardListAdapter;
    String amenityPath;
    public StudyAbroadFacilitiesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public StudyAbroadFacilitiesFragment(List<StudyAbroadAmenitiesDetailsData> amenitiesList, String amenityPath) {
        this.amenitiesList=amenitiesList;
        this.amenityPath=amenityPath;
    }


    // TODO: Rename and change types and number of parameters
    public static StudyAbroadFacilitiesFragment newInstance(String param1, String param2) {
        StudyAbroadFacilitiesFragment fragment = new StudyAbroadFacilitiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_college_facilities, container, false);
        if (amenitiesList!=null) {
            List<CollegeAmenitiesDetailsData> amenitiesDataList = new ArrayList<>();
            CollegeAmenitiesDetailsData collegeData;
            for (StudyAbroadAmenitiesDetailsData data : amenitiesList){
                collegeData = new CollegeAmenitiesDetailsData();
                collegeData.setAmenityID(data.getAmenityID());
                collegeData.setCategoryID(data.getCategoryID());
                collegeData.setImage(data.getImage());
                collegeData.setName(data.getName());
                amenitiesDataList.add(collegeData);
            }
            rvDashboard = v.findViewById(R.id.rv_dashboard);
            linearLayoutManager = new GridLayoutManager(getActivity(), 3);
            rvDashboard.setLayoutManager(linearLayoutManager);
            individualDashboardListAdapter = new IndividualDashboardListAdapter(amenityPath, amenitiesDataList, getActivity(), new IndividualDashboardListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {

                }
            });
            rvDashboard.setAdapter(individualDashboardListAdapter);
        } else {
            v.findViewById(R.id.tv_no_item).setVisibility(View.VISIBLE);
        }
        return v;
    }
  /*  private void setDashboard() {
        categories = new ArrayList<>();
      *//*  categories.add(new IndividulDashboardModel("Wifi",collegeDetailsData.getWifi(),
                R.drawable.ic_wifi));
        categories.add(new IndividulDashboardModel("Library",collegeDetailsData.getLibrary(),
                R.drawable.ic_library));
        categories.add(new IndividulDashboardModel("Hoster",collegeDetailsData.getHostel(),
                R.drawable.ic_hostel));
        categories.add(new IndividulDashboardModel("Shuttle",collegeDetailsData.getShuttile(),
                R.drawable.ic_shuttle));
        categories.add(new IndividulDashboardModel("Canteen",collegeDetailsData.getCanteen(),
                R.drawable.ic_canteen));
        categories.add(new IndividulDashboardModel("Auditorium",collegeDetailsData.getAuditorium(),
                R.drawable.ic_auditorium));
        categories.add(new IndividulDashboardModel("Seminar Hall",collegeDetailsData.getSeminarhall(),
                R.drawable.ic_seminar_hall));
        categories.add(new IndividulDashboardModel("Health Care",collegeDetailsData.getHealthCare(),
                R.drawable.ic_helth_care));
        categories.add(new IndividulDashboardModel("Counselling Cell",collegeDetailsData.getCounsellingSell(),
                R.drawable.ic_counselling));
        categories.add(new IndividulDashboardModel("Sick Room",collegeDetailsData.getSickroom(),
                R.drawable.ic_sick_room));
        categories.add(new IndividulDashboardModel("Store",collegeDetailsData.getStore(),
                R.drawable.ic_store));
        categories.add(new IndividulDashboardModel("Cafeteria",collegeDetailsData.getCaffteria(),
                R.drawable.ic_cafferia));
        categories.add(new IndividulDashboardModel("Workshop",collegeDetailsData.getWorkshop(),
                R.drawable.ic_work_shop));
        *//*
    }*/
}
