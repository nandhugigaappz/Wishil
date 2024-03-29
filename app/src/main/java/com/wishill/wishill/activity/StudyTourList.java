package com.wishill.wishill.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.adapter.StudyTourListAdapter;
import com.wishill.wishill.api.recommendedColleges.SendSchoolEnquery.SendSchoolEnqueryAPI;
import com.wishill.wishill.api.recommendedColleges.SendSchoolEnquery.SendSchoolEnqueryResponse;
import com.wishill.wishill.api.recommendedColleges.studyTourList.StudyTourListAPI;
import com.wishill.wishill.api.recommendedColleges.studyTourList.StudyTourListData;
import com.wishill.wishill.api.recommendedColleges.studyTourList.StudyTourResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

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

public class StudyTourList extends AppCompatActivity {
    String subCatId;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ProgressBar progress;
    TextView tvNoItem;

    RecyclerView rvList;
    LinearLayoutManager linearLayoutManager;
    List<StudyTourListData> studyTourList;
    StudyTourListAdapter studyTourListAdapter;
    public Dialog alertDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tour_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        subCatId=getIntent().getStringExtra("subCatId");
        dialogProgress = new DialogProgress(StudyTourList.this);
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

        rvList=findViewById(R.id.rv_list);
        tvNoItem=findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        progress=findViewById(R.id.progress);
        linearLayoutManager= new LinearLayoutManager(StudyTourList.this);
        rvList.setLayoutManager(linearLayoutManager);
        getList();
    }
    private void getList() {
        retrofit.create(StudyTourListAPI.class).post(subCatId)
                .enqueue(new Callback<StudyTourResponse>() {
                    @Override
                    public void onResponse(Call<StudyTourResponse> call, Response<StudyTourResponse> response) {
                        if (response.isSuccessful()) {
                            progress.setVisibility(View.GONE);
                            if(response.body().getStatus()==1){
                                tvNoItem.setVisibility(View.GONE);
                                studyTourList=response.body().getStudyTourList();
                                studyTourListAdapter=new StudyTourListAdapter(studyTourList, StudyTourList.this, new StudyTourListAdapter.ItemClickAdapterListener() {
                                    @Override
                                    public void itemClick(View v, int position) {

                                    }

                                    @Override
                                    public void studyEnq(View v, int position) {
                                        if(sharedPreferences.getString("login", "false").equals("true")){
                                            sendEnq(studyTourList.get(position).getStudytourID());
                                        }else{
                                            Intent toSlider = new Intent(StudyTourList.this, SocialMediaActivity.class);
                                            startActivity(toSlider);
                                        }

                                    }
                                });
                                rvList.setAdapter(studyTourListAdapter);
                            }else{
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<StudyTourResponse> call, Throwable t) {

                    }
                });
    }
    //contact
    private void sendEnq(final String studyTourID) {
        alertDialog = new Dialog(StudyTourList.this);
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

        fullName.setText(sharedPreferences.getString("userName", ""));
        email.setText(sharedPreferences.getString("userEmail", ""));
        phone.setText(sharedPreferences.getString("userMobile", ""));
        comment.setText(R.string.study_tour_enq);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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
                            enqAPI(strName, strEmail, strPhone,strComment,studyTourID);
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
            Toast.makeText(StudyTourList.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(StudyTourList.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName(String name) {
        if (name.equals("")) {
            Toast.makeText(StudyTourList.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePhone(String mobile) {
        if (mobile.equals("")) {
            Toast.makeText(StudyTourList.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length()!=10) {
                Toast.makeText(StudyTourList.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private void enqAPI(String strName, String strEmail, String strPhone,String strComment,String schoolID) {
        retrofit.create(SendSchoolEnqueryAPI.class).studyTour(strName,strEmail,strPhone,sharedPreferences.getString("userId", ""),schoolID,strComment)
                .enqueue(new Callback<SendSchoolEnqueryResponse>() {
                    @Override
                    public void onResponse(Call<SendSchoolEnqueryResponse> call, Response<SendSchoolEnqueryResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(StudyTourList.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendSchoolEnqueryResponse> call, Throwable t) {
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
