package com.wishill.wishill.collegeFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.BasicDetailsData;

public class CollegeAboutFragment extends Fragment{

    BasicDetailsData collegeDetailsData;

    TextView tvDesc;
    TextView tvEstablishedYear;
    TextView tvAffiliatedTo;
    TextView tvApprovedBy;
    TextView tvAccreditedBy;

    TextView tvMobile;
    TextView tvMail;
    TextView tvWebsite;

    private MapView mMapView;
    private GoogleMap googleMap;




    public CollegeAboutFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CollegeAboutFragment(BasicDetailsData collegeDetailsData) {
        this.collegeDetailsData=collegeDetailsData;
    }


    // TODO: Rename and change types and number of parameters
    public static CollegeAboutFragment newInstance(String param1, String param2) {
        CollegeAboutFragment fragment = new CollegeAboutFragment();
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
        View v=inflater.inflate(R.layout.fragment_college_about, container, false);
        tvDesc=v.findViewById(R.id.tv_des);
        tvEstablishedYear=v.findViewById(R.id.tv_established_year);
        tvAffiliatedTo=v.findViewById(R.id.tv_affiliated_to);
        tvApprovedBy=v.findViewById(R.id.tv_approved_by);
        tvAccreditedBy=v.findViewById(R.id.tv_accredited_by);

        tvMobile=v.findViewById(R.id.tv_mobile);
        tvMail=v.findViewById(R.id.tv_email);
        tvWebsite=v.findViewById(R.id.tv_website);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                double lat = 0;
                double lon=0;
                if(collegeDetailsData.getMapLat()!=null&&!collegeDetailsData.getMapLat().equals("")){
                    lat=Double.parseDouble(collegeDetailsData.getMapLat());
                }
                if(collegeDetailsData.getMapLon()!=null&&!collegeDetailsData.getMapLon().equals("")){
                    lon=Double.parseDouble(collegeDetailsData.getMapLon());
                }
                LatLng location = new LatLng(lat, lon);
                googleMap.addMarker(new MarkerOptions().position(location).title(collegeDetailsData.getName()).snippet(collegeDetailsData.getCity()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        setValues();
        return v;
    }

    private void setValues() {
        tvDesc.setText(android.text.Html.fromHtml(collegeDetailsData.getCampus_details()).toString().trim());
        tvEstablishedYear.setText(collegeDetailsData.getEstablished_year());
        tvAffiliatedTo.setText(collegeDetailsData.getAffiliate_detail_university());
        tvApprovedBy.setText("Not available");
        tvAccreditedBy.setText("Not available");
        //contact
        tvMobile.setText(collegeDetailsData.getPhone());
        tvMail.setText(collegeDetailsData.getEmail());
        tvWebsite.setText(collegeDetailsData.getWebsite());
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}
