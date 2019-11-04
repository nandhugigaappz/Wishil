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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
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
import com.wishill.wishill.api.recommendedColleges.applyScholarship.ApplyScholarshipAPI;
import com.wishill.wishill.api.recommendedColleges.applyScholarship.ApplyScholarshipResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListResponse;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.BasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeAmenitiesDetailsData;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeCoursesDetailsData;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeDetailsResponse;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowAPI;
import com.wishill.wishill.api.recommendedColleges.collegeFollow.SendCollegeFollowResponse;
import com.wishill.wishill.api.recommendedColleges.collegeWishList.SendCollegeWishListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeWishList.SendCollegeWishListResponse;
import com.wishill.wishill.collegeFragments.CollegeAboutFragment;
import com.wishill.wishill.collegeFragments.CollegeContactFragment;
import com.wishill.wishill.collegeFragments.CollegeFacilitiesFragment;
import com.wishill.wishill.collegeFragments.CollegeGalleryFragment;
import com.wishill.wishill.collegeFragments.CollegeNoticesFragment;
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

import static com.wishill.wishill.utilities.Variables.DEFAULT_IMAGE_PATH;

public class CollegeDetailsActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    CollegeAboutFragment  tab1;
    CollegeFacilitiesFragment tab2;
    CollegeGalleryFragment tab3;
    CollegeContactFragment tab4;
    //    CollegeVideoFragment tab5;
    CollegeNoticesFragment tab6;
    String collegeID;
    String userType;

    ImageView ivCover;
    ImageView ivBack;
    ImageView ivLogo;
    TextView tvMyFollowersCount;
    TextView tvCollegeName;
    TextView tvLocation;

    RelativeLayout rlEnq,rlCall;
    TextView tvFollow;
    ImageView ivWishList;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    LinearLayout tabView1, tabView2,tabView3,tabView4,tabView5,tabView6;
    AppBarLayout main;
    ProgressBar progress;
    public Dialog alertDialog;

    SharedPreferences sharedPreferences;

    String userID;

    BasicDetailsData  basicDetailsData;
    List<CollegeAmenitiesDetailsData> amenitiesList;
    List<CollegeCoursesDetailsData> collegeCourseList;
    String collegeImagePath;
    String collegeLogoPath;
    int refer = 0;

    String wishListStatus;
    String followStatus;
    String myFollowersCount;

    String phoneNumber;
    String shareUrl;
    //    ImageView ivShare;
    TextView tvShare;
    TextView tvScholarship;
    String amenityPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userID=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");

        collegeID=getIntent().getStringExtra("collegeID");
        dialogProgress = new DialogProgress(CollegeDetailsActivity.this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        ivBack=findViewById(R.id.iv_back);
        ivCover=findViewById(R.id.iv_cover);
        ivLogo=findViewById(R.id.iv_logo);
        tvCollegeName=findViewById(R.id.tv_college_name);
        tvLocation=findViewById(R.id.tv_location);
        rlEnq=findViewById(R.id.rl_enq);
        rlCall=findViewById(R.id.rl_call);
        main=findViewById(R.id.main);
        main.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        tvFollow=findViewById(R.id.tv_follow);
        ivWishList=findViewById(R.id.iv_wish_list);
        tvMyFollowersCount=findViewById(R.id.tv_my_followers_count);
//        ivShare=findViewById(R.id.iv_share);
        tvShare=findViewById(R.id.tv_share);
        tvScholarship=findViewById(R.id.tv_apply_scholarship);

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
                isEnquiry = true;
                if(sharedPreferences.getString("login", "false").equals("true")){
                    if(userType.equals("normal")){
                        getCourseList(collegeID);
                    }else{
                        Toast.makeText(CollegeDetailsActivity.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent in=new Intent(CollegeDetailsActivity.this,SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                    Toast.makeText(CollegeDetailsActivity.this,"Contact number not available",Toast.LENGTH_SHORT).show();
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
                    collegeFollowAPI();
                }else{
                    Intent toSlider = new Intent(CollegeDetailsActivity.this, SocialMediaActivity.class);
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
                    Intent toSlider = new Intent(CollegeDetailsActivity.this, SocialMediaActivity.class);
                    startActivity(toSlider);
                }
            }
        });
        tvMyFollowersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(CollegeDetailsActivity.this,MyFollowersList.class);
                in.putExtra("institutionId",collegeID);
                in.putExtra("institutionType","1");
                startActivity(in);
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(CollegeDetailsActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        });

        tvScholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEnquiry = false;
                if(sharedPreferences.getString("login", "false").equals("true")){
                    if(userType.equals("normal")){
                        getCourseList(collegeID);
                    }else{
                        Toast.makeText(CollegeDetailsActivity.this,"Partner can't apply scholarship",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent in=new Intent(CollegeDetailsActivity.this,SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(sharedPreferences.getString("login", "false").equals("true")){
                        if(userType.equals("normal")){
                            shareCollege();
                        }else{
                            Toast.makeText(CollegeDetailsActivity.this,"Partner can't share colleges",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Intent in=new Intent(CollegeDetailsActivity.this,SocialMediaActivity.class);
                        startActivity(in);
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CollegeDetailsActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void shareCollege() {
        Intent toShareNEarn = new Intent(CollegeDetailsActivity.this, ShareAndEarnActivity.class);
        toShareNEarn.putExtra("instituteId", collegeID);
        toShareNEarn.putExtra("instituteType", "0");
        toShareNEarn.putExtra("instituteName", tvCollegeName.getText().toString());
        toShareNEarn.putExtra("image",APILinks.IMAGE_LINK+collegeImagePath+basicDetailsData.getCoverImage());
        startActivity(toShareNEarn);
    }

    private void getDetails() {
        retrofit.create(CollegeDetailsAPI.class).post(collegeID,userID)
                .enqueue(new Callback<CollegeDetailsResponse>() {
                    @Override
                    public void onResponse(Call<CollegeDetailsResponse> call, Response<CollegeDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            basicDetailsData=response.body().getDataList().getBasicDetail();
                            amenitiesList=response.body().getDataList().getAmenitiesList();
                            collegeCourseList=response.body().getDataList().getCollegeCourseList();
                            collegeImagePath=response.body().getDataList().getCollegeImgPath();
                            collegeLogoPath=response.body().getDataList().getLogoImgPath();
                            refer=response.body().getDataList().getRefer();
                            amenityPath=response.body().getDataList().getAmenityPath();
                            wishListStatus=response.body().getDataList().getWishCount();
                            followStatus=response.body().getDataList().getFollowCount();
                            myFollowersCount=response.body().getDataList().getMyFollowers();
                            shareUrl=response.body().getDataList().getDetailUrl();
                            main.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            setTabs();
                            setDetails();
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<CollegeDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void setDetails() {
        phoneNumber=basicDetailsData.getPhone();

        Log.e("Cover Image",APILinks.IMAGE_LINK+collegeImagePath+basicDetailsData.getCoverImage());
        Log.e("Logo Image",APILinks.IMAGE_LINK + collegeLogoPath+basicDetailsData.getLogo());

        Glide.with(getApplicationContext()).load(APILinks.IMAGE_LINK+collegeImagePath+basicDetailsData.getCoverImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        DEFAULT_IMAGE_PATH = APILinks.IMAGE_LINK + collegeLogoPath+basicDetailsData.getLogo();
        Glide.with(getApplicationContext()).load(DEFAULT_IMAGE_PATH)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivLogo);
        tvCollegeName.setText(basicDetailsData.getName());
        tvLocation.setText(basicDetailsData.getCity());

        //follow button
        if(followStatus==null||followStatus.equals("0")){
            tvFollow.setBackgroundResource(R.drawable.follow_button);
            tvFollow.setText("Follow");
            tvFollow.setTextColor(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.green));
        }else{
            tvFollow.setBackgroundResource(R.drawable.following_button);
            tvFollow.setText("Following");
            tvFollow.setTextColor(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.white));
        }

        //wish list
        if(wishListStatus==null||wishListStatus.equals("0")){
            ivWishList.setImageResource(R.drawable.ic_wish_list);
        }else{
            ivWishList.setImageResource(R.drawable.ic_wish_list_fill);
        }

        //followers count
        if(myFollowersCount==null||myFollowersCount.equals("")||myFollowersCount.equals("")){
            tvMyFollowersCount.setText("0 Followers");
        }else{
            tvMyFollowersCount.setText(myFollowersCount+" Followers");
        }

        // TODO: 20/10/2019
        if (refer==1){
            tvScholarship.setVisibility(View.VISIBLE);
            tvShare.setVisibility(View.VISIBLE);
        } else {
            tvScholarship.setVisibility(View.GONE);
            tvShare.setVisibility(View.GONE);
        }

    }

    public void setTabs() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);

        tabView1 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title1=tabView1.findViewById(R.id.tv_heading);
        final ImageView imageView1=tabView1.findViewById(R.id.iv_icon);
        imageView1.setImageResource(R.drawable.ic_description);
        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
        title1.setText("About");
        title1.setTextColor(getResources().getColor(R.color.blue));
        title1.setTypeface(null, Typeface.BOLD);
        tabLayout.getTabAt(0).setCustomView(tabView1);

        tabView2 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title2=tabView2.findViewById(R.id.tv_heading);
        final ImageView imageView2=tabView2.findViewById(R.id.iv_icon);
        imageView2.setImageResource(R.drawable.ic_facilities);
        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
        title2.setText("Facilities");
        title2.setTextColor(getResources().getColor(R.color.gray));
        title2.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(1).setCustomView(tabView2);

        tabView4 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title4=tabView4.findViewById(R.id.tv_heading);
        final ImageView imageView4=tabView4.findViewById(R.id.iv_icon);
        imageView4.setImageResource(R.drawable.ic_contact_tab);
        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
        title4.setText("Courses");
        title4.setTextColor(getResources().getColor(R.color.gray));
        title4.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(2).setCustomView(tabView4);

        tabView6 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title6=tabView6.findViewById(R.id.tv_heading);
        final ImageView imageView6=tabView6.findViewById(R.id.iv_icon);
        //imageView5.setImageResource(R.drawable.ic_notice);
        imageView6.setImageResource(R.drawable.notice_board);
        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
        title6.setText("Notice");
        title6.setTextColor(getResources().getColor(R.color.gray));
        title6.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(4).setCustomView(tabView6);

        tabView3 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title3=tabView3.findViewById(R.id.tv_heading);
        final ImageView imageView3=tabView3.findViewById(R.id.iv_icon);
        imageView3.setImageResource(R.drawable.ic_gallery);
        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
        title3.setText("Gallery");
        title3.setTextColor(getResources().getColor(R.color.gray));
        title3.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(3).setCustomView(tabView3);

        /*tabView5 = (LinearLayout) LayoutInflater.from(CollegeDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title5=tabView5.findViewById(R.id.tv_heading);
        final ImageView imageView5=tabView5.findViewById(R.id.iv_icon);
        //imageView5.setImageResource(R.drawable.ic_notice);
        imageView5.setImageResource(R.drawable.ic_gallery);
        imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
        title5.setText("Videos");
        title5.setTextColor(getResources().getColor(R.color.gray));
        title5.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(4).setCustomView(tabView5);*/

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title1.setTextColor(getResources().getColor(R.color.blue));
                        title1.setTypeface(null, Typeface.BOLD);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);
                        title4.setTextColor(getResources().getColor(R.color.gray));

                        /*imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);
                        title5.setTextColor(getResources().getColor(R.color.gray));*/

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title6.setTypeface(null, Typeface.NORMAL);
                        title6.setTextColor(getResources().getColor(R.color.gray));

                        break;

                    case 1:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title2.setTextColor(getResources().getColor(R.color.blue));
                        title2.setTypeface(null, Typeface.BOLD);


                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                       /* imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);*/

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title6.setTextColor(getResources().getColor(R.color.gray));
                        title6.setTypeface(null, Typeface.NORMAL);
                        break;
                    case 2:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title4.setTextColor(getResources().getColor(R.color.blue));
                        title4.setTypeface(null, Typeface.BOLD);

                        /*imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);*/

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title6.setTextColor(getResources().getColor(R.color.gray));
                        title6.setTypeface(null, Typeface.NORMAL);
                        break;

                    case 4:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title6.setTextColor(getResources().getColor(R.color.blue));
                        title6.setTypeface(null, Typeface.BOLD);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                       /* imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);*/

                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);
                        break;

                    case 3:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title3.setTextColor(getResources().getColor(R.color.blue));
                        title3.setTypeface(null, Typeface.BOLD);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        /*imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);*/

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title6.setTextColor(getResources().getColor(R.color.gray));
                        title6.setTypeface(null, Typeface.NORMAL);
                        break;

                   /* case 4:
                        imageView1.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        imageView5.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.blue));
                        title5.setTextColor(getResources().getColor(R.color.blue));
                        title5.setTypeface(null, Typeface.BOLD);

                        imageView6.setColorFilter(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.gray));
                        title6.setTextColor(getResources().getColor(R.color.gray));
                        title6.setTypeface(null, Typeface.NORMAL);
                        break;*/
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
        tab1 = new CollegeAboutFragment(basicDetailsData);
        tab2 = new CollegeFacilitiesFragment(amenitiesList,amenityPath);
        tab3=new CollegeGalleryFragment(collegeID);
        tab4=new CollegeContactFragment(collegeID,collegeCourseList);
//        tab5=new CollegeVideoFragment(collegeID);
        tab6=new CollegeNoticesFragment(collegeID);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "");
        adapter.addFragment(tab2, "");
        adapter.addFragment(tab4, "");
        adapter.addFragment(tab3, "");
//        adapter.addFragment(tab5, "");
        adapter.addFragment(tab6, "");
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


    private void getCourseList(final String collegeID) {
        dialogProgress.show();
        retrofit.create(CollegeCoursesListAPI.class).post(collegeID)
                .enqueue(new Callback<CollegeCoursesListResponse>() {
                    @Override
                    public void onResponse(Call<CollegeCoursesListResponse> call, Response<CollegeCoursesListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            List<CollegeCoursesListData> courseList = response.body().getCourseList();
                            if (courseList == null || courseList.size() == 0) {
                                sendEnq(collegeID, null);
                            } else {
                                sendEnq(collegeID, courseList);
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CollegeCoursesListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }



    //contact
    private boolean isEnquiry = true;
    private void sendEnq(final String collegeID, final List<CollegeCoursesListData> courseList) {
        alertDialog = new Dialog(CollegeDetailsActivity.this);
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
        TextView commentTV = alert_layout.findViewById(R.id.tv_comment);
        Spinner courseSpinner = alert_layout.findViewById(R.id.course_spinner);
        LinearLayout llCourse = alert_layout.findViewById(R.id.ll_course);

        fullName.setText(sharedPreferences.getString("userName", ""));
        email.setText(sharedPreferences.getString("userEmail", ""));
        phone.setText(sharedPreferences.getString("userMobile", ""));
        if (isEnquiry) {
            comment.setText(R.string.college_enq);
            comment.setMinLines(4);
        } else {
            commentTV.setText("Referral Code");
            comment.setText(sharedPreferences.getString("referral", ""));
            comment.setMinLines(1);
            comment.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        final String[] courseId = new String[1];
        if (courseList != null) {
            llCourse.setVisibility(View.VISIBLE);
            final List<String> courseNameList = new ArrayList<>();
            ;
            for (int i = 0; i < courseList.size(); i++) {
                courseNameList.add(courseList.get(i).getCourseName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseNameList);
            courseSpinner.setAdapter(adapter);

            courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    courseId[0] = courseList.get(i).getCourseID();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            llCourse.setVisibility(View.GONE);
            courseId[0] = "";
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strName = fullName.getText().toString();
                final String strEmail = email.getText().toString();
                final String strPhone = phone.getText().toString();
                final String strComment = comment.getText().toString();
                if (validateName(strName)) {
                    if (isEmailValid(strEmail)) {
                        if (validatePhone(strPhone)) {
                            if (isEnquiry) {
                                enqAPI(strName, strEmail, strPhone, strComment, collegeID, courseId[0]);
                            } else {
                                scholarshipAPI(strName, strEmail, strPhone, strComment, collegeID, courseId[0]);
                            }
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

    private void scholarshipAPI(String strName, String strEmail, String strPhone, String reference, String collegeID, String courseId) {
        retrofit.create(ApplyScholarshipAPI.class).post(strName,
                userID,
                strEmail,
                strPhone,
                "1",
                collegeID,
                courseId,
                reference)
                .enqueue(new Callback<ApplyScholarshipResponse>() {
                    @Override
                    public void onResponse(Call<ApplyScholarshipResponse> call, Response<ApplyScholarshipResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(CollegeDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApplyScholarshipResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void enqAPI(String strName, String strEmail, String strPhone, String strComment, String collegeID, String courseId) {
        // sharedPreferences.getString("userId", "")
        retrofit.create(SendCollegeEnqueryAPI.class).post(strName,
                userID,
                strEmail,
                strPhone,
                "1",
                collegeID,
                courseId,
                strComment)
                .enqueue(new Callback<SendCollegeEnqueryResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeEnqueryResponse> call, Response<SendCollegeEnqueryResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(CollegeDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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









    public boolean isEmailValid(String email) {
        if (email.equals("")) {
            Toast.makeText(CollegeDetailsActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(CollegeDetailsActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(CollegeDetailsActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(CollegeDetailsActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length()!=10) {
                Toast.makeText(CollegeDetailsActivity.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }


    private void collegeFollowAPI() {
        dialogProgress.show();
        retrofit.create(SendCollegeFollowAPI.class).post("1",
                collegeID,
                userID)
                .enqueue(new Callback<SendCollegeFollowResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeFollowResponse> call, Response<SendCollegeFollowResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            if(response.body().getSuccess()==1){
                                Toast.makeText(CollegeDetailsActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                if(followStatus == null ||followStatus.equals("0")){
                                    followStatus="1";
                                    tvFollow.setBackgroundResource(R.drawable.following_button);
                                    tvFollow.setText("Following");
                                    tvFollow.setTextColor(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.white));
                                    tvMyFollowersCount.setText((Integer.parseInt(myFollowersCount)+1)+" Followers");
                                }else{
                                    followStatus="0";
                                    tvFollow.setBackgroundResource(R.drawable.follow_button);
                                    tvFollow.setText("Follow");
                                    tvFollow.setTextColor(ContextCompat.getColor(CollegeDetailsActivity.this, R.color.green));
                                    tvMyFollowersCount.setText(myFollowersCount+" Followers");
                                }
                            }else{
                                Toast.makeText(CollegeDetailsActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CollegeDetailsActivity.this,"Failed",Toast.LENGTH_LONG).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SendCollegeFollowResponse> call, Throwable t) {
                        Toast.makeText(CollegeDetailsActivity.this,"Failed",Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();
                    }
                });
    }

    private void wishListAPI() {
        retrofit.create(SendCollegeWishListAPI.class).post("1",
                collegeID,
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
                            Toast.makeText(CollegeDetailsActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        //deep linking
        /*Intent mainIntent = getIntent();
        if (mainIntent!=null && mainIntent.getData()!=null) {
            Uri data = mainIntent.getData();
            List<String> pathSegments = data.getPathSegments();

            if (pathSegments.size() > 0) {
                collegeID = pathSegments.get(3);
            }
            System.out.println("deeplinkingcallback   :- " + pathSegments);
            Toast.makeText(this, "" + collegeID, Toast.LENGTH_SHORT).show();
        }
        getDetails();*/
    }
}
