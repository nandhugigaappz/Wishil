package com.wishill.wishill.activity.jobs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.resumeUpload.ResumeUploadAPI;
import com.wishill.wishill.api.recommendedColleges.resumeUpload.ResumeUploadResponse;
import com.wishill.wishill.jobFragments.AppliedJobFragment;
import com.wishill.wishill.jobFragments.JobListFragment;
import com.wishill.wishill.jobFragments.JobMoreFragment;
import com.wishill.wishill.jobFragments.JobProfileFragment;
import com.wishill.wishill.jobFragments.ProviderJobListFragment;
import com.wishill.wishill.jobFragments.ProviderNotificationFragment;
import com.wishill.wishill.jobFragments.ProviderProfileFragment;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobProviderHome extends AppCompatActivity {

    TextView toolBarTitle;
    DrawerLayout drawer;
    Fragment newFragment;
    String candidate_user_id;
    DialogProgress progress;
    Retrofit retrofit;
    OkHttpClient client;
    HttpLoggingInterceptor interceptor;
    SharedPreferences sharedPreferences;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_provider_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolBarTitle=findViewById(R.id.toolbar_title);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getSharedPreferences("wishill", MODE_PRIVATE);
        candidate_user_id = sharedPreferences.getString("candidate_user_id", "");
        setUpBottomBar();
        newFragment=new JobListFragment();
        beginTransaction();
    }

    AHBottomNavigation bottomNavigation;
    ViewPager viewPager;

    String[] tabNames = new String[]{"Your Jobs", "Notifications", "Profile"};
    private void setUpBottomBar() {
        bottomNavigation =findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Your Jobs", R.drawable.ic_cat, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Notifications", R.drawable.ic_notification, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.ic_profile, R.color.colorPrimary);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#ff5722"));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setAccentColor(Color.parseColor("#1e80a3"));
        bottomNavigation.setInactiveColor(Color.parseColor("#c9cacf"));


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        onBackPressed();
                        return true;
                    case 1:
                        newFragment=new ProviderJobListFragment();
                        beginTransaction();
                        //viewPager.setCurrentItem(0);
                        return true;
                    case 2:
                        newFragment=new ProviderNotificationFragment();
                        beginTransaction();
                        //viewPager.setCurrentItem(1);
                        return true;
                    case 3:
                        newFragment=new ProviderProfileFragment();
                        beginTransaction();
                        //viewPager.setCurrentItem(2);
                        return true;

                }


                return true;
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        onBackPressed();
                        return true;
                    case 1:
                        viewPager.setCurrentItem(0);
                        return true;
                    case 2:
                        viewPager.setCurrentItem(1);
                        return true;
                    case 3:
                        viewPager.setCurrentItem(2);
                        return true;
                    /*case 4:
                        viewPager.setCurrentItem(3);
                        return true;*/
                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigation.setCurrentItem(position+1);
                toolBarTitle.setText(tabNames[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    ProviderJobListFragment tab1;
    ProviderNotificationFragment tab2;
    ProviderProfileFragment tab3;
    ViewPagerAdapter adapter;
    /*private void setupViewPager(ViewPager viewPager) {
        tab1 = new ProviderJobListFragment();
        tab2=new ProviderNotificationFragment();
        tab3=new ProviderProfileFragment();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "");
        adapter.addFragment(tab2, "");
        adapter.addFragment(tab3, "");
        viewPager.setAdapter(adapter);
    }*/

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
                viewPager.setCurrentItem(0);
            }
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    public void beginTransaction(){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    String path = getPath(uri);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                    uploadFile(uri.getPath());
                }
                break;
            case 11:
                if (resultCode == RESULT_OK){
                    newFragment=new JobListFragment();
                    beginTransaction();
                    //setupViewPager(viewPager);
                   /* Fragment frg = null;
                    frg = getSupportFragmentManager().findFragmentByTag();
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();*/
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    //imagesArray = new ArrayList<>();
                    //imagesArray.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;

        }
        if (resultCode == RESULT_OK){
            newFragment=new JobListFragment();
            beginTransaction();
            //setupViewPager(viewPager);
                   /* Fragment frg = null;
                    frg = getSupportFragmentManager().findFragmentByTag();
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();*/
        }
        /*if (resultCode == RESULT_OK){

            setupViewPager(viewPager);
        }*/

        /*if (resultCode == Activity.RESULT_OK) {
         *//* if (requestCode == ID_FILTER) {
                String update = data.getStringExtra("update");
                if (update.equals("yes")) {
                    count = 0;
                    getList();
                    dialogProgress.show();
                }
            }*//*
            Toast.makeText(context, "toast", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void uploadFile(String path) {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(path);

        // Parsing any Media type file
        // RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        // MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("resume", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        progress    = new DialogProgress(JobProviderHome.this);
        progress.show();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), candidate_user_id);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file.getAbsolutePath());
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("resume", file.getName(), requestBody1);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        progress.show();
        ResumeUploadAPI getResponse = retrofit.create(ResumeUploadAPI.class);
        Call<ResumeUploadResponse> call=getResponse.post(requestBody,fileToUpload);
        call.enqueue(new Callback<ResumeUploadResponse>() {
            @Override
            public void onResponse(Call<ResumeUploadResponse> call, Response<ResumeUploadResponse> response) {
                progress.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(JobProviderHome.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResumeUploadResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(JobProviderHome.this, "Some errors occurred", Toast.LENGTH_SHORT).show();

            }
        });
        /*retrofit.create(ResumeUploadAPI.class)
                .post(candidate_user_id, fileToUpload)
                .enqueue(new Callback<ResumeUploadResponse>() {
                    @Override
                    public void onResponse(Call<ResumeUploadResponse> call, Response<ResumeUploadResponse> response) {
                        progress.dismiss();
                        if (response.isSuccessful()){
                            Toast.makeText(JobHomeActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResumeUploadResponse> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(JobHomeActivity.this, "Some errors occurred", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }
    public String getPath(Uri uri){
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void initRetrofit() {
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
                .baseUrl(APILinks.API_LINK_JOBS_CANDIDATE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
