package com.wishill.wishill.schoolFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wishill.wishill.R;
import com.wishill.wishill.adapter.IndividualDashboardListAdapter;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolDetailsData;
import com.wishill.wishill.model.IndividulDashboardModel;

import java.util.ArrayList;
import java.util.List;

public class SchoolFacilitiesFragment extends Fragment {

    RecyclerView rvDashboard;
    LinearLayoutManager linearLayoutManager;
    List<IndividulDashboardModel> categories;
    View v;
    SchoolBasicDetailsData schoolDetailsData;
    IndividualDashboardListAdapter individualDashboardListAdapter;
    public SchoolFacilitiesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SchoolFacilitiesFragment(SchoolBasicDetailsData schoolDetailsData) {
        this.schoolDetailsData=schoolDetailsData;
    }


    // TODO: Rename and change types and number of parameters
    public static SchoolFacilitiesFragment newInstance(String param1, String param2) {
        SchoolFacilitiesFragment fragment = new SchoolFacilitiesFragment();
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
        v=inflater.inflate(R.layout.fragment_college_facilities, container, false);
        rvDashboard = v.findViewById(R.id.rv_dashboard);
        linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvDashboard.setLayoutManager(linearLayoutManager);
       // setDashboard();

        return v;
    }
    private void setDashboard() {
        categories = new ArrayList<>();
       /* categories.add(new IndividulDashboardModel("Library",schoolDetailsData.getLibrary(),
                R.drawable.ic_library));*/
    }
}
