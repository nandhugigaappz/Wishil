package com.wishill.wishill.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.RecommendedCollegeListAdapterSub;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListResponse;
import com.wishill.wishill.mainfragments.HomeFragment;
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

public class RecommentationCollegeActivity extends AppCompatActivity {
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    RecommendedCollegeListAdapterSub recommendedCollegeListAdapter;

    SharedPreferences sharedPreferences;
    String userId,userType;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    public Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommentation_college);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");
        dialogProgress = new DialogProgress(RecommentationCollegeActivity.this);

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

        rvList = findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(RecommentationCollegeActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        recommendedCollegeListAdapter = new RecommendedCollegeListAdapterSub("sub", HomeFragment.recommendedCollegeList, RecommentationCollegeActivity.this, new RecommendedCollegeListAdapterSub.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent in = new Intent(RecommentationCollegeActivity.this, CollegeDetailsActivity.class);
                in.putExtra("collegeID", HomeFragment.recommendedCollegeList.get(position).getCollegeId());
                startActivity(in);
            }

            @Override
            public void itemCall(View v, int position) {
                Toast.makeText(RecommentationCollegeActivity.this,"Pending",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void sendEnq(View v, int position) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    if(userType.equals("normal")){
                        getCourseList(HomeFragment.recommendedCollegeList.get(position).getCollegeId());
                    }else{
                        Toast.makeText(RecommentationCollegeActivity.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent in=new Intent(RecommentationCollegeActivity.this,SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rvList.setAdapter(recommendedCollegeListAdapter);

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
    private void sendEnq(final String collegeID, final List<CollegeCoursesListData> courseList) {
        alertDialog = new Dialog(RecommentationCollegeActivity.this);
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
        Spinner courseSpinner = alert_layout.findViewById(R.id.course_spinner);
        LinearLayout llCourse = alert_layout.findViewById(R.id.ll_course);

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
                            enqAPI(strName, strEmail, strPhone, strComment, collegeID, courseId[0]);
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
    public boolean isEmailValid(String email) {
        if (email.equals("")) {
            Toast.makeText(RecommentationCollegeActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(RecommentationCollegeActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(RecommentationCollegeActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(RecommentationCollegeActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length() != 10) {
                Toast.makeText(RecommentationCollegeActivity.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private void enqAPI(String strName, String strEmail, String strPhone, String strComment, String collegeID, String courseId) {
        retrofit.create(SendCollegeEnqueryAPI.class).post(strName,
                userId,
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
                            Toast.makeText(RecommentationCollegeActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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
