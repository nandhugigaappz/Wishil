package com.wishill.wishill.activity.studyabroad;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.CollegeDetailsActivity;
import com.wishill.wishill.activity.CollegeListActivity;
import com.wishill.wishill.activity.SocialMediaActivity;
import com.wishill.wishill.adapter.CollegeListAdapter;
import com.wishill.wishill.adapter.StudyAbroadListAdapter;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.abroadCourseList.AbroadCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.abroadCourseList.AbroadCoursesListData;
import com.wishill.wishill.api.recommendedColleges.abroadCourseList.AbroadCoursesListResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListResponse;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadListAPI;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadListData;
import com.wishill.wishill.api.recommendedColleges.studyabroadlist.StudyAbroadResponse;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeListAPI;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import com.wishill.wishill.utilities.Variables;

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

public class StudyAbroadList extends AppCompatActivity {
    String countryId;
    String subCatId;
    String userId;
    String userType;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;

    ProgressBar progress;
    TextView tvNoItem;
    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    Dialog alertDialog;

    SharedPreferences sharedPreferences;
    List<StudyAbroadListData> studyAbroadList;
    List<StudyAbroadListData> studyAbroadListRemain;

    int count = 0;
    boolean loadingMain = true;
    StudyAbroadListAdapter  studyAbroadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_abroad_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");

        countryId=getIntent().getStringExtra("countryId");
        subCatId=getIntent().getStringExtra("subCatId");


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

        dialogProgress = new DialogProgress(StudyAbroadList.this);
        rvList = findViewById(R.id.rv_list);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        linearLayoutManager = new LinearLayoutManager(StudyAbroadList.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loadingMain) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadingMain = false;
                            getListRemain();
                        }
                    }
                }
            }
        });


    }

    private void getList() {
        retrofit.create(StudyAbroadListAPI.class).post(
                subCatId,
                countryId)
                .enqueue(new Callback<StudyAbroadResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadResponse> call, Response<StudyAbroadResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus().equals("1")) {
                                tvNoItem.setVisibility(View.GONE);
                                studyAbroadList = response.body().getStudyAbroadList();
                                if(studyAbroadList==null||studyAbroadList.size()==0){
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    rvList.setVisibility(View.GONE);
                                }else{
                                    tvNoItem.setVisibility(View.GONE);
                                    rvList.setVisibility(View.VISIBLE);
                                    setValues();
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                                rvList.setVisibility(View.GONE);
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbroadResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
    private void getListRemain() {
        retrofit.create(StudyAbroadListAPI.class).post(
                subCatId,
                countryId)
                .enqueue(new Callback<StudyAbroadResponse>() {
                    @Override
                    public void onResponse(Call<StudyAbroadResponse> call, Response<StudyAbroadResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            studyAbroadListRemain = response.body().getStudyAbroadList();
                            setValuesRemain();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudyAbroadResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setValues() {
        count = 1;
        boolean loadMore = false;
        if (studyAbroadList.size() == 10) {
            loadMore = true;
            loadingMain = true;
        } else {
            loadMore = false;
            loadingMain = false;
        }
        studyAbroadListAdapter = new StudyAbroadListAdapter(loadMore, studyAbroadList, StudyAbroadList.this, new StudyAbroadListAdapter.ItemClickAdapterListener() {

            @Override
            public void itemClick(View v, int position) {
                   Intent in=new Intent(StudyAbroadList.this,StudyAbroadDetailsActivity.class);
                   in.putExtra("studyabroadID",studyAbroadList.get(position).getStudyabroadID());
                   startActivity(in);
            }

            @Override
            public void itemCall(View v, int position) {
               String phoneNumber =  studyAbroadList.get(position).getPhone();
                if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                    Toast.makeText(StudyAbroadList.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(intent);
                }
            }

            @Override
            public void sendEnq(View v, int position) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    if(userType.equals("normal")){
//                        getCourseList(HomeFragment.recommendedCollegeList.get(position).getCollegeId());
                        getCourseList(studyAbroadList.get(position).getStudyabroadID());
                    }else{
                        Toast.makeText(StudyAbroadList.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent in=new Intent(StudyAbroadList.this,SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rvList.setAdapter(studyAbroadListAdapter);
    }


    private void setValuesRemain() {
        count = count + 1;
        boolean loadMore = false;
        int lastPosition = 0;
        if (studyAbroadListRemain == null || studyAbroadListRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {
            if (studyAbroadListRemain.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = studyAbroadList.size();
            for (int i = 0; i < studyAbroadListRemain.size(); i++) {
                studyAbroadList.add(studyAbroadListRemain.get(i));
            }
        }

        if (loadMore) {
            studyAbroadListAdapter.notifyDataSetChanged();
        } else {
            studyAbroadListAdapter = new StudyAbroadListAdapter(loadMore, studyAbroadList, StudyAbroadList.this, new StudyAbroadListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    Intent in=new Intent(StudyAbroadList.this,StudyAbroadDetailsActivity.class);
                    in.putExtra("studyabroadID",studyAbroadList.get(position).getStudyabroadID());
                    startActivity(in);
                }

                @Override
                public void itemCall(View v, int position) {
                    String phoneNumber =  studyAbroadList.get(position).getPhone();
                    if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                        Toast.makeText(StudyAbroadList.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phoneNumber));
                        startActivity(intent);
                    }
                }

                @Override
                public void sendEnq(View v, int position) {
                    if(sharedPreferences.getString("login", "false").equals("true")){
                        if(userType.equals("normal")){
//                        getCourseList(HomeFragment.recommendedCollegeList.get(position).getCollegeId());
                        }else{
                            Toast.makeText(StudyAbroadList.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Intent in=new Intent(StudyAbroadList.this,SocialMediaActivity.class);
                        startActivity(in);
                    }
                }
            });
            rvList.setAdapter(studyAbroadListAdapter);
            linearLayoutManager.scrollToPosition(lastPosition);
        }
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

    private void getCourseList(final String collegeID) {
        dialogProgress.show();
        retrofit.create(AbroadCoursesListAPI.class).post(collegeID,"1")
                .enqueue(new Callback<AbroadCoursesListResponse>() {
                    @Override
                    public void onResponse(Call<AbroadCoursesListResponse> call, Response<AbroadCoursesListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            List<AbroadCoursesListData> courseList = response.body().getCourseList();
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
                    public void onFailure(Call<AbroadCoursesListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void sendEnq(final String collegeID, final List<AbroadCoursesListData> courseList) {
        alertDialog = new Dialog(StudyAbroadList.this);
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
            Toast.makeText(StudyAbroadList.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(StudyAbroadList.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(StudyAbroadList.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(StudyAbroadList.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length()!=10) {
                Toast.makeText(StudyAbroadList.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private void enqAPI(String strName, String strEmail, String strPhone, String strComment, String collegeID, String courseId) {
        // sharedPreferences.getString("userId", "")
        retrofit.create(SendCollegeEnqueryAPI.class).post(strName,
                userId,
                strEmail,
                strPhone,
                "1",
                collegeID,
                "",
                strComment)
                .enqueue(new Callback<SendCollegeEnqueryResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeEnqueryResponse> call, Response<SendCollegeEnqueryResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(StudyAbroadList.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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
}
