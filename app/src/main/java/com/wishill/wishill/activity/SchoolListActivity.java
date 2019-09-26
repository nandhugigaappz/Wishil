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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.SchoolListAdapter;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery.SendCollegeEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.collegeCourseList.CourseListData;
import com.wishill.wishill.api.recommendedColleges.collegeCourses.CollegeCoursesListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListAPI;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListResponse;
import com.wishill.wishill.api.recommendedColleges.getState.StateListAPI;
import com.wishill.wishill.api.recommendedColleges.getState.StateListData;
import com.wishill.wishill.api.recommendedColleges.getState.StateListResponse;
import com.wishill.wishill.api.recommendedColleges.subCatSchoolList.SubCatSchoolListAPI;
import com.wishill.wishill.api.recommendedColleges.subCatSchoolList.SubCatSchoolListData;
import com.wishill.wishill.api.recommendedColleges.subCatSchoolList.SubCatSchoolResponse;
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

public class SchoolListActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;

    String subCatId;
    String catID;

    SchoolListAdapter schoolListAdapter;
    List<SubCatSchoolListData> schoolList,schoolListRemain;

    public Dialog alertDialog;
    SharedPreferences sharedPreferences;
    int count = 0;
    boolean loadingMain = true;
    static final int ID_FILTER = 1;

    DrawerLayout drawer;
    NavigationView navigationView;
    Spinner stateSpinner;
    Spinner citySpinner;
    TextView btnSearch;
    AutoLabelUI autoLabelLocation;
    TextView tvReset;

    List<StateListData> stateList;
    List<DistrictListData> districtList;
    List<String> stateNameList;
    List<String> districtNameList;
    String userType;
    String userId;
    String phoneNumber;
    String subCatName;
    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        userType=sharedPreferences.getString("userType","");
        tvToolbarTitle=findViewById(R.id.toolbar_title);

        subCatId = getIntent().getStringExtra("subCatId");
        catID = getIntent().getStringExtra("catID");
        subCatName=getIntent().getStringExtra("subCatName");
        userId=sharedPreferences.getString("userId", "");
        tvToolbarTitle.setText(subCatName);

        Variables.COLLEGE_FILTER_STATE_NAME = "";
        Variables.COLLEGE_FILTER_STATE_ID = "";
        Variables.COLLEGE_FILTER_COURSE_NAME = "";
        Variables.COLLEGE_FILTER_COURSE_ID = "";
        Variables.COLLEGE_FILTER_CITY_ID="";
        Variables.COLLEGE_FILTER_CITY_NAME="";
        Variables.COLLEGE_FILTER_CITY_LIST=new ArrayList<>();

        dialogProgress = new DialogProgress(SchoolListActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
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
        linearLayoutManager = new LinearLayoutManager(SchoolListActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();



        //navigation
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_home);
        stateSpinner = navigationView.findViewById(R.id.state_spinner);
        citySpinner = navigationView.findViewById(R.id.city_spinner);
        autoLabelLocation=navigationView.findViewById(R.id.auto_label_location);
        btnSearch=navigationView.findViewById(R.id.btn_search);
        tvReset=navigationView.findViewById(R.id.btn_reset);

        getStateList();

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
                autoLabelLocation.removeAllViews();
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }

                Log.e("State",Variables.COLLEGE_FILTER_STATE_NAME+" ");
                Log.e("State Id",Variables.COLLEGE_FILTER_STATE_ID+" ");

                Log.e("City",Variables.COLLEGE_FILTER_CITY_NAME+" ");
                Log.e("City Id",Variables.COLLEGE_FILTER_CITY_ID+" ");

                count = 0;
                getList();
                dialogProgress.show();
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
        retrofit.create(SubCatSchoolListAPI.class).post(
                subCatId,
                "1",
                Variables.COLLEGE_FILTER_STATE_ID,
                Variables.COLLEGE_FILTER_CITY_ID,
                count + "",
                "")
                .enqueue(new Callback<SubCatSchoolResponse>() {
                    @Override
                    public void onResponse(Call<SubCatSchoolResponse> call, Response<SubCatSchoolResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            if (response.body().getStatus() == 1) {
                                tvNoItem.setVisibility(View.GONE);
                                schoolList = response.body().getSchoolList();
                                if (schoolList == null || schoolList.size() == 0) {
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
                    public void onFailure(Call<SubCatSchoolResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void getListRemain() {
        retrofit.create(SubCatSchoolListAPI.class).post(
                subCatId,
                "1",
                Variables.COLLEGE_FILTER_STATE_ID,
                Variables.COLLEGE_FILTER_CITY_ID,
                count+"",
                Variables.COLLEGE_FILTER_COURSE_ID)
                .enqueue(new Callback<SubCatSchoolResponse>() {
                    @Override
                    public void onResponse(Call<SubCatSchoolResponse> call, Response<SubCatSchoolResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            progress.setVisibility(View.GONE);
                            schoolListRemain=response.body().getSchoolList();
                            setValuesRemain();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubCatSchoolResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }


    private void setValues() {
        count = 1;
        boolean loadMore = false;
        if (schoolList.size() == 10) {
            loadMore = true;
            loadingMain = true;
        } else {
            loadMore = false;
            loadingMain = false;
        }

        schoolListAdapter = new SchoolListAdapter(loadMore, schoolList, SchoolListActivity.this, new SchoolListAdapter.ItemClickAdapterListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent in=new Intent(SchoolListActivity.this,SchoolDetails.class);
                in.putExtra("schoolID",schoolList.get(position).getSchoolId());
                startActivity(in);
            }
            @Override
            public void itemCall(View v, int position) {
                phoneNumber="";
                if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                    Toast.makeText(SchoolListActivity.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(intent);
                }
            }

            @Override
            public void sendEnq(View v, int position) {
                if (sharedPreferences.getString("login", "false").equals("true")) {
                    if (userType.equals("normal")) {
                        sendEnquiry(schoolList.get(position).getSchoolId(),null);
                    } else {
                        Toast.makeText(SchoolListActivity.this, "Partner can't send enquiry", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent in = new Intent(SchoolListActivity.this, SocialMediaActivity.class);
                    startActivity(in);
                }
            }
        });
        rvList.setAdapter(schoolListAdapter);

    }

    private void setValuesRemain() {
        count=count+1;
        boolean loadMore = false;
        int lastPosition = 0;
        if(schoolListRemain==null||schoolListRemain.size()==0){
            loadMore=false;
            loadingMain=false;
        }else{
            if(schoolListRemain.size()==10){
                loadMore=true;
                loadingMain=true;
            }else{
                loadMore=false;
                loadingMain=false;
            }
            lastPosition = schoolList.size();
            for (int i = 0; i < schoolListRemain.size(); i++) {
                schoolList.add(schoolListRemain.get(i));
            }
        }

        if(loadMore){
            schoolListAdapter.notifyDataSetChanged();
        }else{
            schoolListAdapter = new SchoolListAdapter(loadMore, schoolList, SchoolListActivity.this, new SchoolListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                Intent in=new Intent(SchoolListActivity.this,SchoolDetails.class);
                in.putExtra("schoolID",schoolList.get(position).getSchoolId());
                startActivity(in);
                }

                @Override
                public void itemCall(View v, int position) {
                    phoneNumber="";
                    if(phoneNumber==null||phoneNumber.equals("")||phoneNumber.equals("0")){
                        Toast.makeText(SchoolListActivity.this,"Contact number not available",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phoneNumber));
                        startActivity(intent);
                    }
                }

                @Override
                public void sendEnq(View v, int position) {
                    if (sharedPreferences.getString("login", "false").equals("true")) {
                        if (userType.equals("normal")) {
                            sendEnquiry(schoolList.get(position).getSchoolId(),null);
                        } else {
                            Toast.makeText(SchoolListActivity.this, "Partner can't send enquiry", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Intent in = new Intent(SchoolListActivity.this, SocialMediaActivity.class);
                        startActivity(in);
                    }
                }
            });
            rvList.setAdapter(schoolListAdapter);
            linearLayoutManager.scrollToPosition(lastPosition);
        }
    }



    //contact
    private void sendEnquiry(final  String schoolID, final List<CollegeCoursesListData> courseList) {
        alertDialog = new Dialog(SchoolListActivity.this);
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
        Spinner courseSpinner=alert_layout.findViewById(R.id.course_spinner);
        LinearLayout llCourse=alert_layout.findViewById(R.id.ll_course);

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
        if(courseList!=null){
            llCourse.setVisibility(View.VISIBLE);
            final List<String>  courseNameList=new ArrayList<>();;
            for (int i = 0; i < courseList.size(); i++) {
                courseNameList.add(courseList.get(i).getCourseName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseNameList);
            courseSpinner.setAdapter(adapter);

            courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    courseId[0]=courseList.get(i).getCourseID();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else{
            llCourse.setVisibility(View.GONE);
            courseId[0]="";
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strName = fullName.getText().toString();
                final String strEmail = email.getText().toString();
                final String strPhone = phone.getText().toString();
                final String strComment= comment.getText().toString();
                if (validateName(strName)) {
                    if (isEmailValid(strEmail)) {
                        if(validatePhone(strPhone)){
                            enqAPI(strName, strEmail, strPhone,strComment,schoolID,courseId[0]);
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


    private void enqAPI(String strName, String strEmail, String strPhone,String strComment,String collegeID,String courseId) {
        retrofit.create(SendCollegeEnqueryAPI.class).post(
                strName,
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
                            Toast.makeText(SchoolListActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
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

    public boolean isEmailValid(String email) {
        if (email.equals("")) {
            Toast.makeText(SchoolListActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(SchoolListActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(SchoolListActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(SchoolListActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length() != 10) {
                Toast.makeText(SchoolListActivity.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

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
            }

        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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


}
