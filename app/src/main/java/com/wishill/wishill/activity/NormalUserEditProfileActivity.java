package com.wishill.wishill.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListAPI;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListResponse;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileAPI;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileDetailsData;
import com.wishill.wishill.api.recommendedColleges.getProfile.ProfileResponse;
import com.wishill.wishill.api.recommendedColleges.getState.StateListAPI;
import com.wishill.wishill.api.recommendedColleges.getState.StateListData;
import com.wishill.wishill.api.recommendedColleges.getState.StateListResponse;
import com.wishill.wishill.api.recommendedColleges.updateProfile.UpdateProfileApi;
import com.wishill.wishill.api.recommendedColleges.updateProfile.UpdateProfileResponse;
import com.wishill.wishill.dialogBoxes.ListDialogeBox;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.CircleTransform;
import com.wishill.wishill.utilities.DialogProgress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NormalUserEditProfileActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    DialogProgress dialogProgress;
    SharedPreferences sharedPreferences;
    String userID;
    ProfileDetailsData profileDetailsData;

    ImageView ivProfile;
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmail;
    EditText edtPhone;
    TextView tvState;
    TextView tvCity;
    EditText edtAddress;
    RadioGroup rgGender;
    RadioButton rbMale;
    RadioButton rbFemale;
    TextView tvDob;
    EditText edtQualification;
    EditText edtInstitution;
    EditText edtMark;
    TextView tvSubmit;


    List<StateListData> stateList;
    List<DistrictListData> districtList;
    List<String> stateNameList;
    List<String> districtNameList;
    public  static  int selectedPosition=100;

    DatePickerDialog dpd;
    private Calendar calendar;
    private int year, month, day;



    String stateName="";
    String stateId="";
    String cityName="";
    String cityId="";
    String gender="Male";
    String dob="";

    String firstName;
    String lastName;
    String email;
    String mobile;
    String address;
    String qualification;
    String institutionName;
    String mark;
    public  static  String selectedFinalImageUrl="";
    public static String selectedImageUrl = "";
    static final int ID_GALLERY = 1;
    static final int ID_CROP = 2;
    public static Bitmap bt;
    MyThread t1;
    String profilePath;
    ProgressBar progressBar;
    LinearLayout llMain;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectedFinalImageUrl="";
        selectedImageUrl="";
        dialogProgress = new DialogProgress(NormalUserEditProfileActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences =getApplicationContext().getSharedPreferences("wishill",MODE_PRIVATE);
        userID=sharedPreferences.getString("userId","");
        userType = sharedPreferences.getString("userType", "");

        if (ContextCompat.checkSelfPermission(NormalUserEditProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(NormalUserEditProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(NormalUserEditProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


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

        ivProfile=findViewById(R.id.iv_profile);
        edtFirstName=findViewById(R.id.edt_first_name);
        edtLastName=findViewById(R.id.edt_last_name);
        edtEmail=findViewById(R.id.edt_email);
        edtPhone=findViewById(R.id.edt_mobile);
        tvState=findViewById(R.id.tv_state);
        tvCity=findViewById(R.id.tv_city);
        edtAddress=findViewById(R.id.edt_address);
        rgGender=findViewById(R.id.rg_gender);
        rbMale=findViewById(R.id.rb_male);
        rbFemale=findViewById(R.id.rb_female);
        tvDob=findViewById(R.id.tv_dob);
        edtQualification=findViewById(R.id.edt_qualification);
        edtInstitution=findViewById(R.id.edt_institution);
        edtMark=findViewById(R.id.edt_mark);
        tvSubmit=findViewById(R.id.btn_submit);
        progressBar=findViewById(R.id.progress);
        llMain=findViewById(R.id.ll_main);
        llMain.setVisibility(View.GONE);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId==R.id.rb_male){
                    gender="Male";
                }else if(checkedId==R.id.rb_female){
                    gender="Female";
                }
            }
        });


        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateList!=null){
                    selectedPosition = 100;
                    ListDialogeBox dialogBox = new ListDialogeBox(NormalUserEditProfileActivity.this, stateNameList, "Select A State", "editUserProfile");
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (selectedPosition != 100) {
                                stateName = stateList.get(selectedPosition).getState();
                                stateId=stateList.get(selectedPosition).getId();
                                tvState.setText(stateName);
                                tvCity.setText("");
                                tvCity.setHint("Select City");
                                cityName="";
                                dialogProgress.show();
                                getDistrictList();
                            }
                        }
                    });
                }
            }
        });
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(districtNameList!=null){
                    if(!stateName.equals("")){
                        selectedPosition = 100;
                        ListDialogeBox dialogBox = new ListDialogeBox(NormalUserEditProfileActivity.this, districtNameList, "Select A City", "editUserProfile");
                        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogBox.show();
                        dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (selectedPosition != 100) {
                                    cityName = districtList.get(selectedPosition).getCity();
                                    cityId=districtList.get(selectedPosition).getCityId();
                                    tvCity.setText(cityName);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(NormalUserEditProfileActivity.this,"Select a state",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(999);
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName=edtFirstName.getText().toString().trim();
                lastName=edtLastName.getText().toString().trim();
                email=edtEmail.getText().toString().trim();
                mobile=edtPhone.getText().toString().trim();
                address=edtAddress.getText().toString().trim();
                qualification=edtQualification.getText().toString().trim();
                institutionName=edtInstitution.getText().toString().trim();
                mark=edtMark.getText().toString().trim();
                updateProfileDetails();
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(NormalUserEditProfileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NormalUserEditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                } else {
                    if(profileDetailsData.getUserLoginType().equals("Facebook")||profileDetailsData.getUserLoginType().equals("Gmail")){
                         Toast.makeText(NormalUserEditProfileActivity.this,"You can't update profile pic",Toast.LENGTH_SHORT).show();
                    }else{
                        selectedImageUrl = "";
                        Intent in = new Intent(NormalUserEditProfileActivity.this, NormalUserProfileImageBrowse.class);
                        in.putExtra("from","normal");
                        startActivityForResult(in, ID_GALLERY);
                    }
                 /*   Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, ID_GALLERY);*/
                }
            }
        });

        if (userType.equals("normal")) {
            getProfile("0");
        } else {
            String userTypeId = sharedPreferences.getString("userTypeId", "");
            getProfile(userTypeId);
        }

    }

    private void getProfile(String userType) {
        retrofit.create(ProfileAPI.class).post(
                userID,
                userType)
                .enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            profilePath=response.body().getProfileUrl();
                            profileDetailsData=response.body().getProfileData();
                            getStateList();
                        }
                    }
                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {

                    }
                });
    }


    public void updateProfileDetails() {
        dialogProgress.show();
        MultipartBody.Part body = null;
        if (!selectedFinalImageUrl.equals("")) {
            Log.e("Profile","Updating");
            File file = new File(selectedFinalImageUrl);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("userImage", file.getName(), requestFile);
        }
        RequestBody requestBodyUserID = RequestBody.create(MediaType.parse("text/plain"), userID);
        RequestBody requestBodyUserFirstName = RequestBody.create(MediaType.parse("text/plain"), firstName);
        RequestBody requestBodyUserLastName = RequestBody.create(MediaType.parse("text/plain"), lastName);
        RequestBody requestBodyUserEmail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody requestBodyUserPhone = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody requestBodyUserGender = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody requestBodyUserState = RequestBody.create(MediaType.parse("text/plain"), stateId);
        RequestBody requestBodyUserCity = RequestBody.create(MediaType.parse("text/plain"), cityName);
        RequestBody requestBodyUserAddress = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody requestBodyUserQualification = RequestBody.create(MediaType.parse("text/plain"), qualification);
        RequestBody requestBodyUserMark = RequestBody.create(MediaType.parse("text/plain"), mark);
        RequestBody requestBodyUserInstitution= RequestBody.create(MediaType.parse("text/plain"), institutionName);
        RequestBody requestBodyUserDob= RequestBody.create(MediaType.parse("text/plain"), dob);
        retrofit.create(UpdateProfileApi.class)
                .post(requestBodyUserID,
                        requestBodyUserFirstName,
                        requestBodyUserLastName,
                        requestBodyUserEmail,
                        requestBodyUserPhone,
                        requestBodyUserGender,
                        requestBodyUserState,
                        requestBodyUserCity,
                        requestBodyUserAddress,
                        requestBodyUserQualification,
                        requestBodyUserMark,
                        requestBodyUserInstitution,
                        requestBodyUserDob,
                        body).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    dialogProgress.dismiss();
                    if(response.body().getSuccess().equals("1")){
                        Toast.makeText(NormalUserEditProfileActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(NormalUserEditProfileActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialogProgress.dismiss();
                    Toast.makeText(NormalUserEditProfileActivity.this, "Failed to update profile details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                dialogProgress.dismiss();
                Toast.makeText(NormalUserEditProfileActivity.this, "Failed to update profile details", Toast.LENGTH_SHORT).show();
            }
        });

    }





    private void getStateList() {
        //dialogProgress.show();
        retrofit.create(StateListAPI.class).post("1")
                .enqueue(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            stateList=response.body().getCatList();
                            stateNameList = new ArrayList<>();
                            for (int i = 0; i < stateList.size(); i++) {
                                stateNameList.add(stateList.get(i).getState());
                            }
                            setValues();
                        }else{
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void getDistrictList() {
        retrofit.create(DistrictListAPI.class).post(stateId)
                .enqueue(new Callback<DistrictListResponse>() {
                    @Override
                    public void onResponse(Call<DistrictListResponse> call, Response<DistrictListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            districtList=response.body().getCatList();
                            districtNameList = new ArrayList<>();
                            for (int i = 0; i < districtList.size(); i++) {
                                districtNameList.add(districtList.get(i).getCity());
                            }
                        }else{
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<DistrictListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }


    private void setValues() {
        llMain.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        edtFirstName.setText(profileDetailsData.getFirstName());
        edtLastName.setText(profileDetailsData.getLastName());
        if(profileDetailsData.getUserLoginType().equals("Facebook")||profileDetailsData.getUserLoginType().equals("Gmail")){
            Glide.with(ivProfile.getContext()).load(profileDetailsData.getSocial_userImg())
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(ivProfile.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar)
                    .into(ivProfile);
        }else{
            Glide.with(ivProfile.getContext()).load(APILinks.IMAGE_LINK+profilePath+profileDetailsData.getUserImage())
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(ivProfile.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar)
                    .into(ivProfile);
        }

        if(profileDetailsData.getUserLoginType().equals("Facebook")||profileDetailsData.getUserLoginType().equals("Gmail")){
            edtEmail.setEnabled(false);
            edtFirstName.setEnabled(false);
        }else{
            edtPhone.setEnabled(false);
        }
        edtPhone.setText(profileDetailsData.getUserMobile());
        edtEmail.setText(profileDetailsData.getUserEmail());
        stateId=profileDetailsData.getState();
        if(!stateId.equals("")){
            for (int i = 0; i < stateList.size(); i++) {
                if(stateList.get(i).getId().equals(stateId)){
                    stateName=stateList.get(i).getState();
                }
            }
            tvState.setText(stateName);
            getDistrictList();
        }
        cityName=profileDetailsData.getCity();
        tvCity.setText(cityName);
        edtAddress.setText(profileDetailsData.getAddress());

        if(profileDetailsData.getGender().equals("Male")){
            rbMale.setChecked(true);
        }else{
            rbFemale.setChecked(true);
        }
        dob=profileDetailsData.getDateofbirth();
        tvDob.setText(dob);

        edtQualification.setText(profileDetailsData.getHeighestQualication());
        edtInstitution.setText(profileDetailsData.getInstitution());
        if(profileDetailsData.getMark()!=null&&!profileDetailsData.getMark().equals("0")){
            edtMark.setText(profileDetailsData.getMark());
        }
    }

    public void openDatePicker(int x) {
        Calendar cmin = Calendar.getInstance();
        int miny = cmin.get(Calendar.YEAR) - 100;
        int minm = cmin.get(Calendar.MONTH) + 1;
        int mind = cmin.get(Calendar.DAY_OF_MONTH);
        String minDate = mind + "/" + minm + "/" + miny;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateMin = null;
        try {
            dateMin = sdf.parse(minDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long minlong = dateMin.getTime();
        if (x == 999) {
            // dpd = new DatePickerDialog(PersonalInformationActivity.this, myDateListener, year, month, day);
            dpd = new DatePickerDialog(NormalUserEditProfileActivity.this,
                    AlertDialog.THEME_HOLO_LIGHT,myDateListener, year, month, day);
            dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpd.getDatePicker().setMinDate(minlong);
            dpd.show();
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            if (arg0.isShown())
                showDate(arg1, arg2 + 1, arg3);
        }
    };
    private void showDate(int year, int month, int day) {
        dob=dateFormat(year+"-"+month+"-"+day);
        tvDob.setText(dob);
    }

    public  String dateFormat(String day){
        String datenew = null;
        java.text.SimpleDateFormat input = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.text.SimpleDateFormat output = new java.text.SimpleDateFormat("dd MMMM yyyy");
        try {
            Date oneWayTripDate = input.parse(day);
            datenew=output.format(oneWayTripDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datenew;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     if (requestCode == ID_GALLERY) {
             //selectedImageUrl = ImagePath.getPath(this, data.getData());
            if (!selectedImageUrl.equals("")) {
                try {
                    t1=new MyThread("First Thread");
                    t1.start();
                    dialogProgress.show();
                }catch (Exception e){
                    dialogProgress.dismiss();
                }
            }
        } else if (requestCode == ID_CROP) {
            if (!selectedFinalImageUrl.equals("")) {
                Glide.with(ivProfile.getContext()).load(selectedFinalImageUrl)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(ivProfile.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar)
                        .into(ivProfile);
            }
        }
    }

    public class MyThread extends Thread {
        String word;
        public MyThread(String rm) {
            word = rm;
        }
        public void run() {
            bt = BitmapFactory.decodeFile(selectedImageUrl);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
            selectedFinalImageUrl = "";
            Intent in = new Intent(NormalUserEditProfileActivity.this, ProfileImageCropActivity.class);
            in.putExtra("from","normal");
            startActivityForResult(in, ID_CROP);
            dialogProgress.dismiss();
            t1.interrupt();
        }
    }



}
