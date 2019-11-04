package com.wishill.wishill.activity.partnershipwithwishill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.parnershipRegitration.PartnershipRegistrationAPI;
import com.wishill.wishill.api.recommendedColleges.parnershipRegitration.PartnershipRegistrationResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

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

public class PartnerShipRegisterActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    EditText edtUserName;
    EditText edtEmailAddress;
    EditText edtMobile;
    EditText edtPasswordOne;
    EditText edtPasswordTwo;
    TextView tvBtnSubmit;

    String name;
    String email;
    String mobile;
    String passwordOne;
    String passwordTwo;
    public Dialog alertDialog;
    public  static Activity activity;
    RadioGroup rgUSerType;
    RadioButton rbCollege,rbSchool,rbStudyTour;
    String userType="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_ship_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;
        dialogProgress = new DialogProgress(PartnerShipRegisterActivity.this);
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

        edtUserName = findViewById(R.id.edt_use_name);
        edtEmailAddress = findViewById(R.id.edt_email_address);
        edtMobile = findViewById(R.id.edt_mobile);
        edtPasswordOne = findViewById(R.id.edt_pass_one);
        edtPasswordTwo = findViewById(R.id.edt_pass_two);
        tvBtnSubmit = findViewById(R.id.btn_submit);
        rgUSerType=findViewById(R.id.rg_user_type);
        rbCollege=findViewById(R.id.rb_college);
        rbSchool=findViewById(R.id.rb_school);
        rbStudyTour=findViewById(R.id.rb_study_tour);
        rgUSerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId==R.id.rb_college){
                    userType="1";
                    Log.e("userType","College");
                }else if(checkedId==R.id.rb_school){
                    userType="2";
                    Log.e("userType","School");
                }else if(checkedId==R.id.rb_study_tour){
                    userType="3";
                    Log.e("userType","Studytour");
                }
            }
        });
        tvBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtUserName.getText().toString().trim();
                email = edtEmailAddress.getText().toString().trim();
                mobile = edtMobile.getText().toString().trim();
                passwordOne = edtPasswordOne.getText().toString().trim();
                passwordTwo = edtPasswordTwo.getText().toString().trim();
                if(mobile.contains("+91")){
                    mobile=mobile.replace("+91","");
                }
                if (validateName()) {
                    if (isEmailValid()) {
                        if (validatePhone()) {
                            if (validatePassword()) {
                                postSignUpValues();
                            }
                        }
                    }
                }
            }
        });
    }

    private void postSignUpValues() {
        dialogProgress.show();
        retrofit.create(PartnershipRegistrationAPI.class).post(
                userType,
                name,
                email,
                passwordOne,
                mobile)
                .enqueue(new Callback<PartnershipRegistrationResponse>() {
                    @Override
                    public void onResponse(Call<PartnershipRegistrationResponse> call, Response<PartnershipRegistrationResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status = response.body().getSuccess();
                            String message = response.body().getMessage();
                            dialogProgress.dismiss();
                            if(status.equals("1")){
                                Intent in=new Intent(PartnerShipRegisterActivity.this,ParnershipOTPVerificationActivity.class);
                                in.putExtra("mobileNumber",mobile);
                                in.putExtra("userType",userType);
                                in.putExtra("userId",response.body().getData().getUserID());
                                startActivity(in);
                            }else{
                                Toast.makeText(PartnerShipRegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PartnerShipRegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<PartnershipRegistrationResponse> call, Throwable t) {
                        Toast.makeText(PartnerShipRegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
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

    private boolean validateName() {
        if (name.equals("")) {
            Toast.makeText(PartnerShipRegisterActivity.this, "Enter name.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmailValid() {
        if (email.equals("")) {
            Toast.makeText(PartnerShipRegisterActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(PartnerShipRegisterActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }


    private boolean validatePhone() {
        if (mobile.equals("")) {
            Toast.makeText(PartnerShipRegisterActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length() != 10) {
                Toast.makeText(PartnerShipRegisterActivity.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private boolean validatePassword() {
        if (passwordOne.equals("")) {
            Toast.makeText(PartnerShipRegisterActivity.this, "Please enter minimum four digit password.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(passwordOne.length()<4){
                Toast.makeText(PartnerShipRegisterActivity.this, "Please enter minimum four digit password.", Toast.LENGTH_SHORT).show();
                return false;
            }
            /*ArrayList<PasswordValidation> arrayList = Utils.validatePassword(passwordOne);
            if (passwordOne.contains(" ")) {
                Toast.makeText(PartnerShipRegisterActivity.this, "Please enter a Password without space", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                Toast.makeText(PartnerShipRegisterActivity.this, "Password should be Minimum 6 characters length.Minimum 1 Uppercase, Minimum 1 number, Minimum 1 lowercase", Toast.LENGTH_SHORT).show();
                return false;
            }*/
            if (!passwordOne.equals(passwordTwo)) {
                Toast.makeText(PartnerShipRegisterActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }


    private void alertSuccess(int status, String successMessage) {
        alertDialog = new Dialog(PartnerShipRegisterActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        ImageView alertTitle = alert_layout.findViewById(R.id.alertTitle);
        if (status == 1) {
            alertTitle.setImageResource(R.drawable.icon_alert_success);
            title.setText("Success");
        } else {
            alertTitle.setImageResource(R.drawable.ic_alert_close);
            title.setText("Failed");
        }
        message.setText(successMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

}
