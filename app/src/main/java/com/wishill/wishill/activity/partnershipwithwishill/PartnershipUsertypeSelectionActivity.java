package com.wishill.wishill.activity.partnershipwithwishill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wishill.wishill.R;
import com.wishill.wishill.utilities.DialogProgress;

public class PartnershipUsertypeSelectionActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_usertype_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(PartnershipUsertypeSelectionActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in=new Intent(PartnershipUsertypeSelectionActivity.this,PartnerShipRegisterActivity.class);
                startActivity(in);
               // onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
