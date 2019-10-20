package com.wishill.wishill.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.MainActivity;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.partnershipwithwishill.PartnerShipRegisterActivity;
import com.wishill.wishill.mainfragments.CatFragment;
import com.wishill.wishill.mainfragments.FavFragment;
import com.wishill.wishill.mainfragments.HomeFragment;
import com.wishill.wishill.mainfragments.NotificationFragment;
import com.wishill.wishill.mainfragments.ProfileFragment;
import com.wishill.wishill.utilities.APILinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout llLogOut,llChangePassword;
    LinearLayout llMenuOne;
    LinearLayout llPrivacyPolicy;
    LinearLayout llTermsCondition;
    LinearLayout llShareApp;
    LinearLayout llAboutUs;
    LinearLayout llSettings;
    LinearLayout llSendFeed;
    LinearLayout llPartnerLogin;

    ViewPager viewPager;
    AHBottomNavigation bottomNavigation;

    ViewPagerAdapter adapter;
    HomeFragment tab1;
    CatFragment tab2;
    ProfileFragment tab3;
    NotificationFragment tab4;
    FavFragment tab5;
    public  static  Activity home;
    SharedPreferences sharedPreferences;
    String userID;
    String shareCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userID=sharedPreferences.getString("userId","");
        shareCode=sharedPreferences.getString("shareCode","");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        home=this;

        appUpdate();

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

        declaration();

        if(sharedPreferences.getString("login", "false").equals("true")){
            if(sharedPreferences.getString("userType", "").equals("normal")){
                llChangePassword.setVisibility(View.GONE);
            }else{
                llChangePassword.setVisibility(View.VISIBLE);
            }
            llLogOut.setVisibility(View.VISIBLE);

        }else{
            llChangePassword.setVisibility(View.GONE);
            llLogOut.setVisibility(View.GONE);
        }

        llMenuOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(HomeActivity.this,PartnerShipRegisterActivity.class);
                startActivity(in);
            }
        });
        llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                confirmDialog();
            }
        });
        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(HomeActivity.this,PartnerChangePasswordActivity.class);
                startActivity(in);
            }
        });
        llPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                 Intent in=new Intent(HomeActivity.this,TCActivity.class);
                 in.putExtra("from","pp");
                 startActivity(in);
            }
        });
        llTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(HomeActivity.this,TCActivity.class);
                in.putExtra("from","tc");
                startActivity(in);
            }
        });
        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(HomeActivity.this,TCActivity.class);
                in.putExtra("from","about");
                startActivity(in);
            }
        });
        llShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                String refer = "";
                if (!shareCode.equals("")){
                    refer = "&referrer="+shareCode;
                }
                String shareBody = "Wishill App!!\nDownload Now !! \nhttps://play.google.com/store/apps/details?id=com.wishill.wishill"+refer;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "share"));
            }
        });
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(HomeActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        llSendFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(HomeActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        llPartnerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                Intent in=new Intent(HomeActivity.this,PartnerLoginActivity.class);
                startActivity(in);
            }
        });
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        return true;
                    case 1:
                        viewPager.setCurrentItem(1);
                        return true;
                    case 2:
                        viewPager.setCurrentItem(2);
                        return true;
                    case 3:
                        viewPager.setCurrentItem(3);
                        return true;
                    case 4:
                        viewPager.setCurrentItem(4);
                        return true;
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
                bottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void declaration() {
        drawer =findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view_home);
        llMenuOne=navigationView.findViewById(R.id.ll_menu_one);
        llLogOut=navigationView.findViewById(R.id.ll_logout);
        llChangePassword=navigationView.findViewById(R.id.ll_change_password);
        llPrivacyPolicy=navigationView.findViewById(R.id.ll_privacy_policy);
        llTermsCondition=navigationView.findViewById(R.id.ll_terms_conditions);
        llShareApp=navigationView.findViewById(R.id.ll_share_app);
        llAboutUs=navigationView.findViewById(R.id.ll_about_us);
        llSettings=navigationView.findViewById(R.id.ll_settings);
        llSendFeed=navigationView.findViewById(R.id.ll_send_feedback);
        llPartnerLogin=navigationView.findViewById(R.id.ll_partner_login);

        llPartnerLogin.setVisibility(View.GONE);

        bottomNavigation =findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Categories", R.drawable.ic_cat, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Profile", R.drawable.ic_profile, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Notification", R.drawable.ic_notification, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Wishlist", R.drawable.ic_favorite, R.color.colorPrimary);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#ff5722"));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setAccentColor(Color.parseColor("#1e80a3"));
        bottomNavigation.setInactiveColor(Color.parseColor("#c9cacf"));



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);



    }
    private void setupViewPager(ViewPager ViewPager) {
        tab1 = new HomeFragment();
        tab2=new CatFragment();
        tab3=new ProfileFragment();
        tab4=new NotificationFragment();
        tab5=new FavFragment();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "");
        adapter.addFragment(tab2, "");
        adapter.addFragment(tab3, "");
        adapter.addFragment(tab4, "");
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent in=new Intent(HomeActivity.this,SearchActivity.class);
            startActivity(in);
            return true;
        }else if(id==android.R.id.home){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    private void confirmDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_confirm);

        TextView tvCancel=dialog.findViewById(R.id.tv_cancel);
        TextView tvSubmit=dialog.findViewById(R.id.tv_submit);
        TextView tvHeading=dialog.findViewById(R.id.tv_heading);
        TextView tvMessage=dialog.findViewById(R.id.tv_message);
        tvHeading.setText("Logout");
        tvMessage.setText("Do you want to logout ??");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", "false");
                editor.putString("userType", "");
                editor.putString("userId", "");
                editor.putString("userTypeId", "");
                editor.putString("shareCode", "");
                editor.commit();
                finish();
                Intent toSlider = new Intent(HomeActivity.this, SocialMediaActivity.class);
                toSlider.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toSlider);

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void appUpdate(){
        try {
            AppUpdater appUpdater = new AppUpdater(HomeActivity.this);
            appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
            appUpdater.setDisplay(Display.DIALOG);
            appUpdater.setCancelable(false);
            appUpdater.setButtonDoNotShowAgain("");
            appUpdater.start();
        }catch (Exception e){

        }
    }

}
