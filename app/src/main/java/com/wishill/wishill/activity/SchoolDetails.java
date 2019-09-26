package com.wishill.wishill.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;

import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.SendSchoolEnquery.SendSchoolEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendSchoolEnquery.SendSchoolEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowAPI;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowResponse;
import com.wishill.wishill.api.recommendedColleges.collegeWishList.SendCollegeWishListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeWishList.SendCollegeWishListResponse;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolDetailsData;
import com.wishill.wishill.api.recommendedColleges.schoolDetails.SchoolDetailsResponse;
import com.wishill.wishill.schoolFragments.SchoolAboutFragment;
import com.wishill.wishill.schoolFragments.SchoolFacilitiesFragment;
import com.wishill.wishill.schoolFragments.SchoolFeesFragment;
import com.wishill.wishill.schoolFragments.SchoolGalleryFragment;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchoolDetails extends AppCompatActivity {
    DialogProgress dialogProgress;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    String schoolID;

    SchoolAboutFragment tab1;
    SchoolFacilitiesFragment tab2;
    SchoolGalleryFragment tab3;
    SchoolFeesFragment tab4;

    ImageView ivCover;
    ImageView ivBack;
    ImageView ivLogo;
    TextView tvCollegeName;
    TextView tvLocation;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    LinearLayout tabView1, tabView2,tabView3,tabView4;
    AppBarLayout main;
    ProgressBar progress;
    RelativeLayout rlEnq,rlCall;
    TextView tvFollow;
    ImageView ivWishList;
    SharedPreferences sharedPreferences;

    String userID;
    String userType;

    String schoolImagePath;
    String schoolLogoPath;
    SchoolBasicDetailsData schoolBasicDetailsData;
    String followStatus;
    String wishListStatus;
    String phoneNumber;
    public Dialog alertDialog;
    String shareUrl;
    ImageView ivShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);

        userID=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");


        schoolID=getIntent().getStringExtra("schoolID");
        dialogProgress = new DialogProgress(SchoolDetails.this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        ivBack=findViewById(R.id.iv_back);
        ivCover=findViewById(R.id.iv_cover);
        ivLogo=findViewById(R.id.iv_logo);
        rlEnq=findViewById(R.id.rl_enq);
        rlCall=findViewById(R.id.rl_call);
        tvCollegeName=findViewById(R.id.tv_college_name);
        tvLocation=findViewById(R.id.tv_location);
        main=findViewById(R.id.main);
        main.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        tvFollow=findViewById(R.id.tv_follow);
        ivWishList=findViewById(R.id.iv_wish_list);
        ivShare=findViewById(R.id.iv_share);

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

        getDetails();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("login", "false").equals("true")) {
                    if (userType.equals("normal")) {
                        sendEnquiry(schoolID,null);
                    } else {
                        Toast.makeText(SchoolDetails.this, "Partner can't send enquiry", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent in = new Intent(SchoolDetails.this, SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                    Toast.makeText(SchoolDetails.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(intent);
                }
            }
        });
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    schoolFollowAPI();;
                }else{
                    Intent toSlider = new Intent(SchoolDetails.this, SocialMediaActivity.class);
                    startActivity(toSlider);
                }
            }
        });
        ivWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    wishListAPI();
                }else{
                    Intent toSlider = new Intent(SchoolDetails.this, SocialMediaActivity.class);
                    startActivity(toSlider);
                }
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "Wishill App!!\n"+schoolBasicDetailsData.getSchoolName()+"\n"+APILinks.MAIN_URL+shareUrl;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "share"));
            }
        });

    }

    private void getDetails() {
        retrofit.create(SchoolDetailsAPI.class).post(schoolID,userID)
                .enqueue(new Callback<SchoolDetailsResponse>() {
                    @Override
                    public void onResponse(Call<SchoolDetailsResponse> call, Response<SchoolDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            SchoolDetailsData detailsData=response.body().getDetailsData();
                            schoolImagePath=detailsData.getSchoolImgPath();
                            schoolLogoPath=detailsData.getLogoImgPath();
                            schoolBasicDetailsData=detailsData.getSchoolBasicDetailsData();
                            followStatus=detailsData.getFollowCount();
                            wishListStatus=detailsData.getWishCount();
                            shareUrl=detailsData.getDetailUrl();

                            main.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            setTabs();
                            setDetails();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<SchoolDetailsResponse> call, Throwable t) {

                    }
                });
    }
    public void setTabs() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        tabView1 = (LinearLayout) LayoutInflater.from(SchoolDetails.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title1=tabView1.findViewById(R.id.tv_heading);
        final ImageView imageView1=tabView1.findViewById(R.id.iv_icon);
        imageView1.setImageResource(R.drawable.ic_description);
        imageView1.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.blue));
        title1.setText("About");
        title1.setTextColor(getResources().getColor(R.color.blue));
        title1.setTypeface(null, Typeface.BOLD);
        tabLayout.getTabAt(0).setCustomView(tabView1);

        tabView2 = (LinearLayout) LayoutInflater.from(SchoolDetails.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title2=tabView2.findViewById(R.id.tv_heading);
        final ImageView imageView2=tabView2.findViewById(R.id.iv_icon);
        imageView2.setImageResource(R.drawable.ic_facilities);
        imageView2.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
        title2.setText("Facilities");
        title2.setTextColor(getResources().getColor(R.color.gray));
        title2.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(1).setCustomView(tabView2);


        tabView3 = (LinearLayout) LayoutInflater.from(SchoolDetails.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title3=tabView3.findViewById(R.id.tv_heading);
        final ImageView imageView3=tabView3.findViewById(R.id.iv_icon);
        imageView3.setImageResource(R.drawable.ic_gallery);
        imageView3.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
        title3.setText("Gallery");
        title3.setTextColor(getResources().getColor(R.color.gray));
        title3.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(2).setCustomView(tabView3);

        tabView4 = (LinearLayout) LayoutInflater.from(SchoolDetails.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title4=tabView4.findViewById(R.id.tv_heading);
        final ImageView imageView4=tabView4.findViewById(R.id.iv_icon);
        imageView4.setImageResource(R.drawable.ic_contact_tab);
        imageView4.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
        title4.setText("Fees");
        title4.setTextColor(getResources().getColor(R.color.gray));
        title4.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(3).setCustomView(tabView4);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        imageView1.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.blue));
                        title1.setTextColor(getResources().getColor(R.color.blue));
                        title1.setTypeface(null, Typeface.BOLD);

                        imageView2.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        break;

                    case 1:
                        imageView1.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.blue));
                        title2.setTextColor(getResources().getColor(R.color.blue));
                        title2.setTypeface(null, Typeface.BOLD);

                        imageView3.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        break;
                    case 2:
                        imageView1.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.blue));
                        title3.setTextColor(getResources().getColor(R.color.blue));
                        title3.setTypeface(null, Typeface.BOLD);

                        imageView4.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        break;
                    case 3:
                        imageView1.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(SchoolDetails.this, R.color.blue));
                        title4.setTextColor(getResources().getColor(R.color.blue));
                        title4.setTypeface(null, Typeface.BOLD);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    private void setupViewPager(ViewPager ViewPager) {
        tab1 = new SchoolAboutFragment(schoolBasicDetailsData);
        tab2 = new SchoolFacilitiesFragment(schoolBasicDetailsData);
        tab3=new SchoolGalleryFragment(schoolID);
        tab4=new SchoolFeesFragment(schoolID);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "");
        adapter.addFragment(tab2, "");
        adapter.addFragment(tab3, "");
        adapter.addFragment(tab4, "");
        ViewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setDetails() {
        phoneNumber = schoolBasicDetailsData.getTel();
        Log.e("School Cover",APILinks.IMAGE_LINK +schoolImagePath+ schoolBasicDetailsData.getSchoolCover());
        Log.e("School Logo",APILinks.IMAGE_LINK +schoolLogoPath+ schoolBasicDetailsData.getSchoolLogo());
        Glide.with(ivCover.getContext()).load(APILinks.IMAGE_LINK +schoolImagePath+ schoolBasicDetailsData.getSchoolCover())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);

        Glide.with(ivLogo.getContext()).load(APILinks.IMAGE_LINK +schoolLogoPath+ schoolBasicDetailsData.getSchoolLogo())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivLogo);

        tvCollegeName.setText(schoolBasicDetailsData.getSchoolName());
        tvLocation.setText(schoolBasicDetailsData.getSchoolCity());

        //follow button
        if (followStatus == null || followStatus.equals("0")) {
            tvFollow.setBackgroundResource(R.drawable.follow_button);
            tvFollow.setText("Follow");
            tvFollow.setTextColor(ContextCompat.getColor(SchoolDetails.this, R.color.green));
        } else {
            tvFollow.setBackgroundResource(R.drawable.following_button);
            tvFollow.setText("Following");
            tvFollow.setTextColor(ContextCompat.getColor(SchoolDetails.this, R.color.white));
        }

        //wish list
        if (wishListStatus == null || wishListStatus.equals("0")) {
            ivWishList.setImageResource(R.drawable.ic_wish_list);
        } else {
            ivWishList.setImageResource(R.drawable.ic_wish_list_fill);
        }
    }


    public boolean isEmailValid(String email) {
        if (email.equals("")) {
            Toast.makeText(SchoolDetails.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(SchoolDetails.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(SchoolDetails.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(SchoolDetails.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length()!=10) {
                Toast.makeText(SchoolDetails.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }



    private void schoolFollowAPI() {
        dialogProgress.show();
        retrofit.create(SendCollegeFollowAPI.class).post("2",
                schoolID,
                userID)
                .enqueue(new Callback<SendCollegeFollowResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeFollowResponse> call, Response<SendCollegeFollowResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            if(response.body().getSuccess()==1){
                                Toast.makeText(SchoolDetails.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                if(followStatus.equals("0")){
                                    followStatus="1";
                                    tvFollow.setBackgroundResource(R.drawable.following_button);
                                    tvFollow.setText("Following");
                                    tvFollow.setTextColor(ContextCompat.getColor(SchoolDetails.this, R.color.white));
                                }else{
                                    followStatus="0";
                                    tvFollow.setBackgroundResource(R.drawable.follow_button);
                                    tvFollow.setText("Follow");
                                    tvFollow.setTextColor(ContextCompat.getColor(SchoolDetails.this, R.color.green));

                                }
                            }else{
                                Toast.makeText(SchoolDetails.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SchoolDetails.this,"Failed",Toast.LENGTH_LONG).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SendCollegeFollowResponse> call, Throwable t) {
                        Toast.makeText(SchoolDetails.this,"Failed",Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();
                    }
                });
    }


    //contact
    private void sendEnquiry(final  String schoolID, final List<CollegeCoursesListData> courseList) {
        alertDialog = new Dialog(SchoolDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_owner_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText fullName = alert_layout.findViewById(R.id.edt_name);
        final EditText email = alert_layout.findViewById(R.id.edt_email);
        final EditText phone = alert_layout.findViewById(R.id.edt_phone);
        final EditText comment = alert_layout.findViewById(R.id.edt_comment);
        TextView submit = alert_layout.findViewById(R.id.tv_submit);
        Spinner courseSpinner=alert_layout.findViewById(R.id.course_spinner);
        LinearLayout llCourse=alert_layout.findViewById(R.id.ll_course);

        fullName.setText(sharedPreferences.getString("userName", ""));
        email.setText(sharedPreferences.getString("userEmail", ""));
        phone.setText(sharedPreferences.getString("userMobile", ""));
        comment.setText(R.string.college_enq);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        final String[] courseId = new String[1];
        if(courseList!=null){
            llCourse.setVisibility(View.VISIBLE);
            final List<String>  courseNameList=new ArrayList<>();;
            for (int i = 0; i < courseList.size(); i++) {
                courseNameList.add(courseList.get(i).getCourseName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseNameList);
            courseSpinner.setAdapter(adapter);

            courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    courseId[0]=courseList.get(i).getCourseID();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else{
            llCourse.setVisibility(View.GONE);
            courseId[0]="";
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strName = fullName.getText().toString();
                final String strEmail = email.getText().toString();
                final String strPhone = phone.getText().toString();
                final String strComment= comment.getText().toString();
                if (validateName(strName)) {
                    if (isEmailValid(strEmail)) {
                        if(validatePhone(strPhone)){
                            enqAPI(strName, strEmail, strPhone,strComment,schoolID,courseId[0]);
                            dialogProgress.show();
                            alertDialog.dismiss();
                        }
                    }
                }
            }
        });
        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }


    private void enqAPI(String strName, String strEmail, String strPhone,String strComment,String collegeID,String courseId) {
        retrofit.create(SendCollegeEnqueryAPI.class).post(
                strName,
                userID,
                strEmail,
                strPhone,
                "2",
                collegeID,
                courseId,
                strComment)
                .enqueue(new Callback<SendCollegeEnqueryResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeEnqueryResponse> call, Response<SendCollegeEnqueryResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(SchoolDetails.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SendCollegeEnqueryResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void wishListAPI() {
        retrofit.create(SendCollegeWishListAPI.class).post("2",
                schoolID,
                userID)
                .enqueue(new Callback<SendCollegeWishListResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeWishListResponse> call, Response<SendCollegeWishListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            if(wishListStatus.equals("0")){
                                wishListStatus="1";
                                ivWishList.setImageResource(R.drawable.ic_wish_list_fill);
                            }else{
                                wishListStatus="0";
                                ivWishList.setImageResource(R.drawable.ic_wish_list);
                            }
                            Toast.makeText(SchoolDetails.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SendCollegeWishListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }









}
