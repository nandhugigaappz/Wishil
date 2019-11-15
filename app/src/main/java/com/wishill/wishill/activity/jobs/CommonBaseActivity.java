package com.wishill.wishill.activity.jobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wishill.wishill.R;
import com.wishill.wishill.jobFragments.AppliedJobFragment;
import com.wishill.wishill.jobFragments.JobProfileFragment;

public class CommonBaseActivity extends AppCompatActivity {

    String TAG = "CommonBaseActivity";
    SharedPreferences sharedPreferences;

    Context context;
    private Fragment[] fragmentList;
    private String[] titleList;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_base);

        context = CommonBaseActivity.this;

        title   = findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        sharedPreferences = getSharedPreferences("wishill", MODE_PRIVATE);

        Intent intent = getIntent();
        int method = intent.getIntExtra("method", 0);

        fragmentList    = new Fragment[]{new JobProfileFragment(), new AppliedJobFragment()};
        titleList       = new String[]{"Profile", "Applied Jobs"};

        openMethod(method);

    }

    private void openMethod(int method) {
        title.setText(titleList[method]);
        openPage(fragmentList[method]);
    }

    private void openPage(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_common_base, fragment);
        fragmentTransaction.commit();
    }

/*
    public void openFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_common_base, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
