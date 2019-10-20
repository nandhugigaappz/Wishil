package com.wishill.wishill.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.CollegeListAdapter;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourseList.CourseListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourseList.CourseListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourseList.CourseListResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListAPI;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListResponse;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListAPI;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListResponse;
import com.wishill.wishill.api.recommendedColleges.getState.StateListAPI;
import com.wishill.wishill.api.recommendedColleges.getState.StateListData;
import com.wishill.wishill.api.recommendedColleges.getState.StateListResponse;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeListAPI;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeListData;
import com.wishill.wishill.api.recommendedColleges.subCatCollegeList.SubCatCollegeResponse;
import com.wishill.wishill.datamodel.CityDataModel;
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

public class CollegeListActivity extends AppCompatActivity {
    String subCatId;
    String catID;
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
    List<SubCatCollegeListData> catList, catListRemain;
    CollegeListAdapter collegeListAdapter;
    public Dialog alertDialog;
    SharedPreferences sharedPreferences;

    static final int ID_FILTER = 1;
    boolean loadingMain = true;
    int count = 0;

    DrawerLayout drawer;
    NavigationView navigationView;
    Spinner stateSpinner;
    Spinner citySpinner;
    Spinner courseSpinner;
    TextView btnSearch;
    AutoLabelUI autoLabelLocation;
    TextView tvReset;


    List<StateListData> stateList;
    List<DistrictListData> districtList;
    List<CourseListData> courseList;
    List<String> stateNameList;
    List<String> districtNameList;
    List<String> courseNameList;

    String phoneNumber;
    String subCatName;
    String countryID = "";
    String country = "";
    TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarTitle=findViewById(R.id.toolbar_title);

        Variables.COLLEGE_FILTER_STATE_NAME = "";
        Variables.COLLEGE_FILTER_STATE_ID = "";
        Variables.COLLEGE_FILTER_COURSE_NAME = "";
        Variables.COLLEGE_FILTER_COURSE_ID = "";
        Variables.COLLEGE_FILTER_CITY_ID="";
        Variables.COLLEGE_FILTER_CITY_NAME="";
        Variables.COLLEGE_FILTER_CITY_LIST=new ArrayList<>();

        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userId=sharedPreferences.getString("userId", "");
        userType=sharedPreferences.getString("userType","");
        subCatId = getIntent().getStringExtra("subCatId");
        catID = getIntent().getStringExtra("catID");
        subCatName=getIntent().getStringExtra("subCatName");
        country=getIntent().getStringExtra("country");
        countryID=getIntent().getStringExtra("countryID");
        dialogProgress = new DialogProgress(CollegeListActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolBarTitle.setText(subCatName);

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
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        linearLayoutManager = new LinearLayoutManager(CollegeListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();

        //navigation
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_home);
        stateSpinner = navigationView.findViewById(R.id.state_spinner);
        citySpinner = navigationView.findViewById(R.id.city_spinner);
        autoLabelLocation=navigationView.findViewById(R.id.auto_label_location);
        courseSpinner=navigationView.findViewById(R.id.course_spinner);
        btnSearch=navigationView.findViewById(R.id.btn_search);
        tvReset=navigationView.findViewById(R.id.btn_reset);
   /*     autoLabelLocation.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {

            }
        });*/
        getStateList();
        getCourseList();

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.COLLEGE_FILTER_STATE_NAME = "";
                Variables.COLLEGE_FILTER_STATE_ID = "";
                Variables.COLLEGE_FILTER_COURSE_NAME = "";
                Variables.COLLEGE_FILTER_COURSE_ID = "";
                Variables.COLLEGE_FILTER_CITY_ID="";
                Variables.COLLEGE_FILTER_CITY_NAME="";
                Variables.COLLEGE_FILTER_CITY_LIST=new ArrayList<>();
                setStateSpinner();
                setCourseSpinner();
                autoLabelLocation.removeAllViews();
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }

                Log.e("State",Variables.COLLEGE_FILTER_STATE_NAME+" ");
                Log.e("State Id",Variables.COLLEGE_FILTER_STATE_ID+" ");

                Log.e("City",Variables.COLLEGE_FILTER_CITY_NAME+" ");
                Log.e("City Id",Variables.COLLEGE_FILTER_CITY_ID+" ");

                Log.e("Course",Variables.COLLEGE_FILTER_COURSE_NAME+" ");
                Log.e("Course Id",Variables.COLLEGE_FILTER_COURSE_ID+" ");



                count = 0;
                getList();
                dialogProgress.show();
            }
        });

        autoLabelLocation.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {
                for (int i=0; i<Variables.COLLEGE_FILTER_CITY_LIST.size(); i++){
                    CityDataModel model = Variables.COLLEGE_FILTER_CITY_LIST.get(i);
                    if (model.getCityName().equals(((Label) view).getText())){
                        Variables.COLLEGE_FILTER_CITY_LIST.remove(model);
                    }
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables.COLLEGE_FILTER_CITY_NAME="";
                Variables.COLLEGE_FILTER_CITY_ID="";
                for(int i=0;i<Variables.COLLEGE_FILTER_CITY_LIST.size();i++){
                    if(Variables.COLLEGE_FILTER_CITY_NAME.equals("")){
                        Variables.COLLEGE_FILTER_CITY_NAME=Variables.COLLEGE_FILTER_CITY_LIST.get(i).getCityName();
                        Variables.COLLEGE_FILTER_CITY_ID=Variables.COLLEGE_FILTER_CITY_LIST.get(i).getCityId();
                    }else{
                        Variables.COLLEGE_FILTER_CITY_NAME=Variables.COLLEGE_FILTER_CITY_NAME+","+Variables.COLLEGE_FILTER_CITY_LIST.get(i).getCityName();
                        Variables.COLLEGE_FILTER_CITY_ID=Variables.COLLEGE_FILTER_CITY_ID+","+Variables.COLLEGE_FILTER_CITY_LIST.get(i).getCityId();
                    }
                }
                Log.e("State",Variables.COLLEGE_FILTER_STATE_NAME+" ");
                Log.e("State Id",Variables.COLLEGE_FILTER_STATE_ID+" ");

                Log.e("City",Variables.COLLEGE_FILTER_CITY_NAME+" ");
                Log.e("City Id",Variables.COLLEGE_FILTER_CITY_ID+" ");

                Log.e("Course",Variables.COLLEGE_FILTER_COURSE_NAME+" ");
                Log.e("Course Id",Variables.COLLEGE_FILTER_COURSE_ID+" ");

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                count = 0;
                getList();
                dialogProgress.show();
            }
        });


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
        retrofit.create(SubCatCollegeListAPI.class).post(
                subCatId,
                countryID,
                Variables.COLLEGE_FILTER_STATE_ID,
                Variables.COLLEGE_FILTER_CITY_ID,
                count + "",
                Variables.COLLEGE_FILTER_COURSE_ID)
                .enqueue(new Callback<SubCatCollegeResponse>() {
                    @Override
                    public void onResponse(Call<SubCatCollegeResponse> call, Response<SubCatCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                tvNoItem.setVisibility(View.GONE);
                                catList = response.body().getCatList();
                                if (catList == null || catList.size() == 0) {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    rvList.setVisibility(View.GONE);
                                } else {
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
                    public void onFailure(Call<SubCatCollegeResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void getListRemain() {
        retrofit.create(SubCatCollegeListAPI.class).post(
                subCatId,
                countryID,
                Variables.COLLEGE_FILTER_STATE_ID,
                Variables.COLLEGE_FILTER_CITY_ID,
                count + "",
                Variables.COLLEGE_FILTER_COURSE_ID)
                .enqueue(new Callback<SubCatCollegeResponse>() {
                    @Override
                    public void onResponse(Call<SubCatCollegeResponse> call, Response<SubCatCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            catListRemain = response.body().getCatList();
                            setValuesRemain();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubCatCollegeResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }


    private void setValues() {
        count = 1;
        boolean loadMore = false;
        if (catList.size() == 10) {
            loadMore = true;
            loadingMain = true;
        } else {
            loadMore = false;
            loadingMain = false;
        }
        collegeListAdapter = new CollegeListAdapter(loadMore, catList, CollegeListActivity.this, new CollegeListAdapter.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent in=new Intent(CollegeListActivity.this,CollegeDetailsActivity.class);
                in.putExtra("collegeID",catList.get(position).getCollegeID());
                startActivity(in);
            }

            @Override
            public void itemCall(View v, int position) {
                phoneNumber=catList.get(position).getPhone();
                if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                    Toast.makeText(CollegeListActivity.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(intent);
                }
            }

            @Override
            public void itemSendEnq(View v, int position) {
                if(sharedPreferences.getString("login", "false").equals("true")){
                    if(userType.equals("normal")){
                        getCourseList(catList.get(position).getCollegeID());
                    }else{
                        Toast.makeText(CollegeListActivity.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent in=new Intent(CollegeListActivity.this,SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rvList.setAdapter(collegeListAdapter);
    }


    private void setValuesRemain() {
        count = count + 1;
        boolean loadMore = false;
        int lastPosition = 0;
        if (catListRemain == null || catListRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {
            if (catListRemain.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = catList.size();
            for (int i = 0; i < catListRemain.size(); i++) {
                catList.add(catListRemain.get(i));
            }
        }

        if (loadMore) {
            collegeListAdapter.notifyDataSetChanged();
        } else {
            collegeListAdapter = new CollegeListAdapter(loadMore, catList, CollegeListActivity.this, new CollegeListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    Intent in=new Intent(CollegeListActivity.this,CollegeDetailsActivity.class);
                    in.putExtra("collegeID",catList.get(position).getCollegeID());
                    startActivity(in);
                }
                @Override
                public void itemCall(View v, int position) {
                    phoneNumber="";
                    if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                        Toast.makeText(CollegeListActivity.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phoneNumber));
                        startActivity(intent);
                    }

                }
                @Override
                public void itemSendEnq(View v, int position) {
                    if(sharedPreferences.getString("login", "false").equals("true")){
                        if(userType.equals("normal")){
                            getCourseList(catList.get(position).getCollegeID());
                        }else{
                            Toast.makeText(CollegeListActivity.this,"Partner can't send enquiry",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Intent in=new Intent(CollegeListActivity.this,SocialMediaActivity.class);
                        startActivity(in);
                    }
                }
            });
            rvList.setAdapter(collegeListAdapter);
            linearLayoutManager.scrollToPosition(lastPosition);
        }
    }

    //contact
    private void sendEnq(final String collegeID, final List<CollegeCoursesListData> courseList) {
        alertDialog = new Dialog(CollegeListActivity.this);
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
            Toast.makeText(CollegeListActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(CollegeListActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(CollegeListActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(CollegeListActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length() != 10) {
                Toast.makeText(CollegeListActivity.this, "Please enter a valid mobile number",
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
                catID,
                collegeID,
                courseId,
                strComment)
                .enqueue(new Callback<SendCollegeEnqueryResponse>() {
                    @Override
                    public void onResponse(Call<SendCollegeEnqueryResponse> call, Response<SendCollegeEnqueryResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(CollegeListActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {

            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ID_FILTER) {
                String update = data.getStringExtra("update");
                if (update.equals("yes")) {
                    count = 0;
                    getList();
                    dialogProgress.show();
                }
            }
        }
    }

    private void getStateList() {
        retrofit.create(StateListAPI.class).post("1")
                .enqueue(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            stateList = response.body().getCatList();
                            stateNameList = new ArrayList<>();
                            stateNameList.add("Select State");
                            for (int i = 0; i < stateList.size(); i++) {
                                stateNameList.add(stateList.get(i).getState());
                            }
                            setStateSpinner();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setStateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateNameList);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    Variables.COLLEGE_FILTER_STATE_ID = stateList.get(i-1).getId();
                    Variables.COLLEGE_FILTER_STATE_NAME = stateList.get(i-1).getState();
                    Variables.COLLEGE_FILTER_CITY_LIST=new ArrayList<>();
                    Variables.COLLEGE_FILTER_CITY_NAME="";
                    Variables.COLLEGE_FILTER_CITY_ID="";
                    autoLabelLocation.removeAllViews();
                    getDistrictList();
                }else{
                    Variables.COLLEGE_FILTER_STATE_ID="";
                    Variables.COLLEGE_FILTER_STATE_NAME="";
                    getDistrictList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDistrictList() {
        retrofit.create(DistrictListAPI.class).post(Variables.COLLEGE_FILTER_STATE_ID)
                .enqueue(new Callback<DistrictListResponse>() {
                    @Override
                    public void onResponse(Call<DistrictListResponse> call, Response<DistrictListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            districtList = response.body().getCatList();
                            districtNameList = new ArrayList<>();
                            districtNameList.add("Select City");
                            for (int i = 0; i < districtList.size(); i++) {
                                districtNameList.add(districtList.get(i).getCity());
                            }
                            setCitySpinner();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DistrictListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setCitySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, districtNameList);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    autoLabelLocation.addLabel(districtList.get(i-1).getCity());
                    CityDataModel cityDataModel=new CityDataModel();
                    cityDataModel.setCityName(districtList.get(i-1).getCity());
                    cityDataModel.setCityId(districtList.get(i-1).getCityId());
                    Variables.COLLEGE_FILTER_CITY_LIST.add(cityDataModel);
                    Variables.COLLEGE_FILTER_CITY_NAME="";
                    Variables.COLLEGE_FILTER_CITY_ID="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCourseList() {
        retrofit.create(CourseListAPI.class).post(subCatId)
                .enqueue(new Callback<CourseListResponse>() {
                    @Override
                    public void onResponse(Call<CourseListResponse> call, Response<CourseListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            courseList=response.body().getCatList();
                            courseNameList = new ArrayList<>();
                            courseNameList.add("Select Course");
                            for (int i = 0; i < courseList.size(); i++) {
                                courseNameList.add(courseList.get(i).getCourseName());
                            }
                            setCourseSpinner();
                        }else{
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<CourseListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setCourseSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseNameList);
        courseSpinner.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    Variables.COLLEGE_FILTER_COURSE_NAME = courseList.get(i-1).getCourseName();
                    Variables.COLLEGE_FILTER_COURSE_ID= courseList.get(i-1).getCourseID();
                }else{
                    Variables.COLLEGE_FILTER_COURSE_NAME="";
                    Variables.COLLEGE_FILTER_COURSE_ID="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


}
