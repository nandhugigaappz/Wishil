package com.wishill.wishill.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.examList.ExamListData;
import com.wishill.wishill.utilities.DialogProgress;

public class ExamDetailsPage extends AppCompatActivity {
    DialogProgress dialogProgress;
    ExamListData examListData;

    TextView tvTitle;
    TextView tvLocation;
    TextView tvExamMode;
    TextView tvRegDate;
    TextView tvCourseOffered;
    TextView tvCourseLevel;
    TextView tvExamDuration;
    TextView tvExamMedium;
    TextView tvTotalQuestion;
    TextView tvTotalMark;
    TextView tvMobile;
    TextView tvEmail;
    TextView tvWebsite;
    TextView tvDec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(ExamDetailsPage.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        examListData=(ExamListData) getIntent().getSerializableExtra("examData");

        tvTitle=findViewById(R.id.tv_title);
        tvLocation=findViewById(R.id.tv_location);
        tvExamMode=findViewById(R.id.tv_exam_mode);
        tvRegDate=findViewById(R.id.tv_reg_date);
        tvCourseOffered=findViewById(R.id.tv_course_offered);
        tvCourseLevel=findViewById(R.id.tv_course_level);
        tvExamDuration=findViewById(R.id.tv_exam_duration);
        tvExamMedium=findViewById(R.id.tv_exam_medium);
        tvTotalQuestion=findViewById(R.id.tv_total_question);
        tvTotalMark=findViewById(R.id.tv_total_mark);
        tvMobile=findViewById(R.id.tv_mobile);
        tvEmail=findViewById(R.id.tv_email);
        tvWebsite=findViewById(R.id.tv_website);
        tvDec=findViewById(R.id.tv_des);

        tvTitle.setText(examListData.getExamTitle());
        tvLocation.setText(examListData.getCity());
        tvExamMode.setText(examListData.getExamMode());
        tvRegDate.setText(examListData.getRegDate());
        tvCourseOffered.setText(examListData.getCourseOffered());
        tvCourseLevel.setText(examListData.getCourseLevel());
        tvExamDuration.setText(examListData.getExamDuration());
        tvExamMedium.setText(examListData.getExamMedium());
        tvTotalQuestion.setText(examListData.getTotalQuestion());
        tvTotalMark.setText(examListData.getTotalMark());
        tvMobile.setText(examListData.getMobile());
        tvWebsite.setText(examListData.getWebsite());
        tvEmail.setText(examListData.getEmail());
        tvDec.setText(examListData.getDescription());


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
