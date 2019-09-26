package com.wishill.wishill.activity.studyabroad;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.studyabroadfragments.StudyAbroadAboutFragment;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.collegeDetails.CollegeDetailsResponse;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadAmenitiesDetailsData;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadBasicDetailsData;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadCoursesDetailsData;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadDetailsAPI;
import com.wishill.wishill.api.recommendedColleges.studyabroaddetails.StudyAbroadDetailsResponse;
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

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudyAbroadDetailsActivity extends AppCompatActivity {
    String studyabroadID;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    SharedPreferences sharedPreferences;
    String userId;
    String userType;

    LinearLayout tabView1, tabView2,tabView3,tabView4,tabView5;
    StudyAbroadAboutFragment tab1;
    StudyAbroadAboutFragment tab2;
    StudyAbroadAboutFragment tab3;
    StudyAbroadAboutFragment tab4;
    StudyAbroadAboutFragment tab5;
    ViewPagerAdapter adapter;




    ImageView ivCover;
    ImageView ivBack;
    ImageView ivLogo;
    TextView tvMyFollowersCount;
    TextView tvCollegeName;
    TextView tvLocation;
    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout rlEnq,rlCall;
    TextView tvFollow;
    ImageView ivWishList;
    AppBarLayout main;
    ProgressBar progress;
    ImageView ivShare;

    StudyAbroadBasicDetailsData basicDetailsData;
    List<StudyAbroadAmenitiesDetailsData> amenitiesList;
    List<StudyAbroadCoursesDetailsData> itemCourse;
    String collegeImagePath;
    String collegeLogoPath;
    String wishListStatus;
    String followStatus;
    String myFollowersCount;
    String phoneNumber;
    String shareUrl;
    String amenityPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_abroad_details);
        studyabroadID=getIntent().getStringExtra("studyabroadID");

        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");


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

        dialogProgress = new DialogProgress(StudyAbroadDetailsActivity.this);
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
        ivShare=findViewById(R.id.iv_share);

        getDetails();

    }

    private void getDetails() {
        retrofit.create(StudyAbroadDetailsAPI.class).post(userId,studyabroadID)
                .enqueue(new Callback<StudyAbroadDetailsResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadDetailsResponse> call, Response<StudyAbroadDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            basicDetailsData=response.body().getDataList().getBasicDetail();
                            amenitiesList=response.body().getDataList().getAmenitiesList();
                            itemCourse=response.body().getDataList().getItemCourse();
                            collegeImagePath=response.body().getDataList().getItemImgpath();
                            collegeLogoPath=response.body().getDataList().getLogoImgPath();
                            amenityPath=response.body().getDataList().getAmenityPath();
                            wishListStatus=response.body().getDataList().getWishCount();
                            followStatus=response.body().getDataList().getFollowCount();
                            myFollowersCount=response.body().getDataList().getMyFollowers();
                            shareUrl=response.body().getDataList().getDetailUrl();
                            phoneNumber=basicDetailsData.getPhone();
                            main.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            setTabs();
                            setDetails();
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<StudyAbroadDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void setDetails() {
        phoneNumber=basicDetailsData.getPhone();

        Log.e("Cover Image",APILinks.IMAGE_LINK+collegeImagePath+basicDetailsData.getCoverImage());
        Log.e("Logo Image",APILinks.IMAGE_LINK + collegeLogoPath+basicDetailsData.getLogo());

        Glide.with(ivCover.getContext()).load(APILinks.IMAGE_LINK+collegeImagePath+basicDetailsData.getCoverImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);
        Glide.with(ivLogo.getContext()).load(APILinks.IMAGE_LINK + collegeLogoPath+basicDetailsData.getLogo())
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
            tvFollow.setTextColor(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.green));
        }else{
            tvFollow.setBackgroundResource(R.drawable.following_button);
            tvFollow.setText("Following");
            tvFollow.setTextColor(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.white));
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


    }

    public void setTabs() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        tabView1 = (LinearLayout) LayoutInflater.from(StudyAbroadDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title1=tabView1.findViewById(R.id.tv_heading);
        final ImageView imageView1=tabView1.findViewById(R.id.iv_icon);
        imageView1.setImageResource(R.drawable.ic_description);
        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
        title1.setText("About");
        title1.setTextColor(getResources().getColor(R.color.blue));
        title1.setTypeface(null, Typeface.BOLD);
        tabLayout.getTabAt(0).setCustomView(tabView1);

        tabView2 = (LinearLayout) LayoutInflater.from(StudyAbroadDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title2=tabView2.findViewById(R.id.tv_heading);
        final ImageView imageView2=tabView2.findViewById(R.id.iv_icon);
        imageView2.setImageResource(R.drawable.ic_facilities);
        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
        title2.setText("Facilities");
        title2.setTextColor(getResources().getColor(R.color.gray));
        title2.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(1).setCustomView(tabView2);


        tabView3 = (LinearLayout) LayoutInflater.from(StudyAbroadDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title3=tabView3.findViewById(R.id.tv_heading);
        final ImageView imageView3=tabView3.findViewById(R.id.iv_icon);
        imageView3.setImageResource(R.drawable.ic_gallery);
        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
        title3.setText("Gallery");
        title3.setTextColor(getResources().getColor(R.color.gray));
        title3.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(3).setCustomView(tabView3);

        tabView4 = (LinearLayout) LayoutInflater.from(StudyAbroadDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title4=tabView4.findViewById(R.id.tv_heading);
        final ImageView imageView4=tabView4.findViewById(R.id.iv_icon);
        imageView4.setImageResource(R.drawable.ic_contact_tab);
        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
        title4.setText("Courses");
        title4.setTextColor(getResources().getColor(R.color.gray));
        title4.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(2).setCustomView(tabView4);

        tabView5 = (LinearLayout) LayoutInflater.from(StudyAbroadDetailsActivity.this)
                .inflate(R.layout.custom_tab_one, null, false);
        final TextView title5=tabView5.findViewById(R.id.tv_heading);
        final ImageView imageView5=tabView5.findViewById(R.id.iv_icon);
        //imageView5.setImageResource(R.drawable.ic_notice);
        imageView5.setImageResource(R.drawable.ic_gallery);
        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
        title5.setText("Videos");
        title5.setTextColor(getResources().getColor(R.color.gray));
        title5.setTypeface(null, Typeface.NORMAL);
        tabLayout.getTabAt(4).setCustomView(tabView5);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
                        title1.setTextColor(getResources().getColor(R.color.blue));
                        title1.setTypeface(null, Typeface.BOLD);

                        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);
                        break;

                    case 1:
                        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
                        title2.setTextColor(getResources().getColor(R.color.blue));
                        title2.setTypeface(null, Typeface.BOLD);


                        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);
                        break;
                    case 2:
                        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
                        title4.setTextColor(getResources().getColor(R.color.blue));
                        title4.setTypeface(null, Typeface.BOLD);

                        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);





                        break;
                    case 3:
                        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
                        title3.setTextColor(getResources().getColor(R.color.blue));
                        title3.setTypeface(null, Typeface.BOLD);

                        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title5.setTextColor(getResources().getColor(R.color.gray));
                        title5.setTypeface(null, Typeface.NORMAL);
                        break;
                    case 4:
                        imageView1.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title1.setTextColor(getResources().getColor(R.color.gray));
                        title1.setTypeface(null, Typeface.NORMAL);

                        imageView2.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title2.setTextColor(getResources().getColor(R.color.gray));
                        title2.setTypeface(null, Typeface.NORMAL);

                        imageView3.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title3.setTextColor(getResources().getColor(R.color.gray));
                        title3.setTypeface(null, Typeface.NORMAL);

                        imageView4.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.gray));
                        title4.setTextColor(getResources().getColor(R.color.gray));
                        title4.setTypeface(null, Typeface.NORMAL);

                        imageView5.setColorFilter(ContextCompat.getColor(StudyAbroadDetailsActivity.this, R.color.blue));
                        title5.setTextColor(getResources().getColor(R.color.blue));
                        title5.setTypeface(null, Typeface.BOLD);
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
        tab1 = new StudyAbroadAboutFragment(basicDetailsData);
        tab2 = new StudyAbroadAboutFragment(basicDetailsData);
        tab3=new StudyAbroadAboutFragment(basicDetailsData);
        tab4=new StudyAbroadAboutFragment(basicDetailsData);
        tab5=new StudyAbroadAboutFragment(basicDetailsData);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "");
        adapter.addFragment(tab2, "");
        adapter.addFragment(tab4, "");
        adapter.addFragment(tab3, "");
        adapter.addFragment(tab5, "");
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


}
