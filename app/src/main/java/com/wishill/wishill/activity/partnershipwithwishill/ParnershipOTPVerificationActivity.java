package com.wishill.wishill.activity.partnershipwithwishill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP.SignUpOTPVerificationAPI;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP.SignUpOTPVerificationResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParnershipOTPVerificationActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    TextView tvSubTitle;
    EditText edtOtp;
    TextView tvButtonSubmit;
    TextView tvResend;

    String mobileNumber;
    String userType;
    String userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parnership_otpverification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        dialogProgress = new DialogProgress(ParnershipOTPVerificationActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        userType=getIntent().getStringExtra("userType");
        userId=getIntent().getStringExtra("userId");
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
        tvSubTitle = findViewById(R.id.text_subTitle);
        edtOtp = findViewById(R.id.edt_otp);
        tvButtonSubmit = findViewById(R.id.btn_submit);
        tvResend = findViewById(R.id.tv_resend);
        tvSubTitle.setText("We have sent the verification code to your mobile number- +91" + mobileNumber);
        tvButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edtOtp.getText().toString().trim();
                if(!otp.equals("")){
                    otpVerification(otp);
                }
            }
        });
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ParnershipOTPVerificationActivity.this, "Resend", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void otpVerification(String otp){
        dialogProgress.show();
        retrofit.create(SignUpOTPVerificationAPI.class).post(otp,userId)
                .enqueue(new Callback<SignUpOTPVerificationResponse>() {
                    @Override
                    public void onResponse(Call<SignUpOTPVerificationResponse> call, Response<SignUpOTPVerificationResponse> response) {
                        if (response.isSuccessful()){
                            dialogProgress.dismiss();
                            String status=response.body().getSuccess();
                            if(status.equals("1")){
                                if(userType.equals("1")){

                                    Intent in=new Intent(ParnershipOTPVerificationActivity.this,AddPartnerCollegeActivity.class);
                                    in.putExtra("partnerId",userId);
                                    in.putExtra("userType","1");
                                    startActivity(in);

                                }else if(userType.equals("2")){

                                    Intent in=new Intent(ParnershipOTPVerificationActivity.this,AddPartnerCollegeActivity.class);
                                    in.putExtra("partnerId",userId);
                                    in.putExtra("userType","2");
                                    startActivity(in);

                                }else if(userType.equals("3")){

                                    Toast.makeText(ParnershipOTPVerificationActivity.this,"Pending",Toast.LENGTH_SHORT).show();

                                }
                                PartnerShipRegisterActivity.activity.finish();
                                finish();
                            }else{
                                Toast.makeText(ParnershipOTPVerificationActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(ParnershipOTPVerificationActivity.this, "OTP Verification failed", Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<SignUpOTPVerificationResponse> call, Throwable t) {
                        Toast.makeText(ParnershipOTPVerificationActivity.this,"OTP Verification failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

  /*  private void otpVerification(String otp) {
        dialogProgress.show();
        retrofit.create(OTPVerificationAPI.class).post(
                mobileNumber,
                otp
        )
                .enqueue(new Callback<OTPVerificationResponse>() {
                    @Override
                    public void onResponse(Call<OTPVerificationResponse> call, Response<OTPVerificationResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            if(status==1){
                                Toast.makeText(ParnershipOTPVerificationActivity.this,message,Toast.LENGTH_SHORT).show();
                                List<OTPVerificationData> dataList=response.body().getDataList();
                                String partnerId=dataList.get(0).getPartnerId();
                                if(userType.equals("College")){
                                    Intent in=new Intent(ParnershipOTPVerificationActivity.this,AddPartnerCollegeActivity.class);
                                    in.putExtra("partnerId",partnerId);
                                    startActivity(in);
                                }else if(userType.equals("School")){
                                    Intent in=new Intent(ParnershipOTPVerificationActivity.this,AddPartnerScoolActivity.class);
                                    in.putExtra("partnerId",partnerId);
                                    startActivity(in);
                                }else if(userType.equals("Studytour")){
                                    Intent in=new Intent(ParnershipOTPVerificationActivity.this,AddPartnerStudyTourActivity.class);
                                    in.putExtra("partnerId",partnerId);
                                    startActivity(in);
                                }
                                PartnerShipRegisterActivity.activity.finish();
                                finish();
                            }else{
                               Toast.makeText(ParnershipOTPVerificationActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ParnershipOTPVerificationActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<OTPVerificationResponse> call, Throwable t) {
                        Toast.makeText(ParnershipOTPVerificationActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }*/

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
