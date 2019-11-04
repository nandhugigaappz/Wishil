package com.wishill.wishill.activity.resumebuilder;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.resumebuilder.datamodel.Resume;
import com.wishill.wishill.activity.resumebuilder.fragments.EducationFragment;
import com.wishill.wishill.activity.resumebuilder.fragments.EssentialsFragment;
import com.wishill.wishill.activity.resumebuilder.fragments.ExperienceFragment;
import com.wishill.wishill.activity.resumebuilder.fragments.PersonalInfoFragment;
import com.wishill.wishill.activity.resumebuilder.fragments.PreviewFragment;
import com.wishill.wishill.activity.resumebuilder.fragments.ProjectsFragment;

import java.util.ArrayList;
import java.util.List;

public class ResumeBuilderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Resume resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_builder);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String json = mPrefs.getString("SerializableObject", "");
        if (json.isEmpty())
            resume = Resume.createNewResume();
        else
            resume = gson.fromJson(json, Resume.class);

       /* if (savedInstanceState == null) {
            PersonalInfoFragment.newInstance(resume);
//            openFragment();
//            currentTitle = getString(R.string.navigation_personal_info);
        }*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(resume);
        prefsEditor.putString("SerializableObject", json);
        prefsEditor.apply();
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT_TITLE, currentTitle);
    }*/

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PersonalInfoFragment().newInstance(resume), "Personal Info");
        adapter.addFragment(new EducationFragment().newInstance(resume), "Education");
        adapter.addFragment(new ExperienceFragment().newInstance(resume), "Experience");
        adapter.addFragment(new ProjectsFragment().newInstance(resume), "Projects Info");
        adapter.addFragment(new EssentialsFragment().newInstance(resume), "Essentials");
        adapter.addFragment(new PreviewFragment().newInstance(resume), "Preview");
        viewPager.setAdapter(adapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
