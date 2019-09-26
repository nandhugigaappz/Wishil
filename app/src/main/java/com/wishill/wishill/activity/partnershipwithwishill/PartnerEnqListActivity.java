package com.wishill.wishill.activity.partnershipwithwishill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wishill.wishill.R;
import com.wishill.wishill.adapter.PartnerEnqListAdapter;
import com.wishill.wishill.mainfragments.ProfileFragment;
import com.wishill.wishill.utilities.DialogProgress;

public class PartnerEnqListActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    PartnerEnqListAdapter partnerEnqListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_enq_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(PartnerEnqListActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        rvList=findViewById(R.id.rv_list);
        linearLayoutManager= new LinearLayoutManager(PartnerEnqListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        partnerEnqListAdapter=new PartnerEnqListAdapter(ProfileFragment.enqList, PartnerEnqListActivity.this, new PartnerEnqListAdapter.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {

            }
        });
        rvList.setAdapter(partnerEnqListAdapter);
    }

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
