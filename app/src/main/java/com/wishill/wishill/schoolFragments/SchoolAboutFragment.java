package com.wishill.wishill.schoolFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolDetailsData;

public class SchoolAboutFragment extends Fragment {
    SchoolBasicDetailsData schoolDetailsData;
    TextView tvDesc;
    TextView tvEstablishedYear;
    TextView tvAffiliatedTo;
    TextView tvSchoolType;
    TextView tvEducationOffered;
    TextView tvLabs;
    TextView tvNearBy;
    TextView tvSports;

    TextView tvMobile;
    TextView tvMail;
    TextView tvWebsite;

    private MapView mMapView;
    private GoogleMap googleMap;
    public SchoolAboutFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SchoolAboutFragment(SchoolBasicDetailsData schoolDetailsData) {
        this.schoolDetailsData=schoolDetailsData;
    }


    // TODO: Rename and change types and number of parameters
    public static SchoolAboutFragment newInstance(String param1, String param2) {
        SchoolAboutFragment fragment = new SchoolAboutFragment();
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
        View v=inflater.inflate(R.layout.fragment_school_about, container, false);
        tvDesc=v.findViewById(R.id.tv_des);
        tvEstablishedYear=v.findViewById(R.id.tv_established_year);
        tvAffiliatedTo=v.findViewById(R.id.tv_affiliated_to);
        tvSchoolType=v.findViewById(R.id.tv_school_type);
        tvEducationOffered=v.findViewById(R.id.tv_education_offered);
        tvLabs=v.findViewById(R.id.tv_labs);
        tvNearBy=v.findViewById(R.id.tv_near_by);
        tvSports=v.findViewById(R.id.tv_sports);

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
                double lat=Double.parseDouble(schoolDetailsData.getLattitude());
                double lon=Double.parseDouble(schoolDetailsData.getLongitude());
                LatLng location = new LatLng(lat, lon);
                googleMap.addMarker(new MarkerOptions().position(location).title(schoolDetailsData.getSchoolName()).snippet(schoolDetailsData.getSchoolCity()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        setValues();

        return v;
    }

    private void setValues() {
        tvDesc.setText(android.text.Html.fromHtml(schoolDetailsData.getSchoolDesc()).toString().trim());
        tvEstablishedYear.setText(schoolDetailsData.getSchoolEstYear());
        tvAffiliatedTo.setText(schoolDetailsData.getSchoolAffil());
        tvSchoolType.setText("");
        tvEducationOffered.setText(schoolDetailsData.getEducationOffered());
        tvLabs.setText(schoolDetailsData.getLabs());
        tvNearBy.setText(schoolDetailsData.getNearby());
        tvSports.setText(schoolDetailsData.getSports());

        tvMobile.setText(schoolDetailsData.getTel());
        tvMail.setText(schoolDetailsData.getEmail());
        tvWebsite.setText(schoolDetailsData.getWebsite());

    }

}
