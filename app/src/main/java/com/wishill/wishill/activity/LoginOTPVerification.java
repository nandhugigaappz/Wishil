package com.wishill.wishill.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP.SignUpOTPVerificationAPI;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP.SignUpOTPVerificationResponse;
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

public class LoginOTPVerification extends AppCompatActivity {
    String mobileNumber;
    TextView tvSubTitle;
    EditText edtOTP;
    TextView btnSubmit;
    SharedPreferences sharedPreferences;
    ImageView ivBack;
    DialogProgress dialogProgress;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    String otp;
    String userId;
    String userType;
    String userTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otpverification);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        mobileNumber=getIntent().getStringExtra("mobileNumber");
        userId=getIntent().getStringExtra("userId");
        userType=getIntent().getStringExtra("userType");
        userTypeId=getIntent().getStringExtra("userTypeId");
        dialogProgress=new DialogProgress(LoginOTPVerification.this);
        startSmsRetrever();
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

        tvSubTitle=findViewById(R.id.text_subTitle);
        edtOTP=findViewById(R.id.edt_otp);
        btnSubmit=findViewById(R.id.btn_submit);
        ivBack=findViewById(R.id.iv_back);
        tvSubTitle.setText("We have sent the verification code to your mobile number- +91" + mobileNumber);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp=edtOTP.getText().toString().trim();
                if(!otp.equals("")){
                    signUpMobileOTP();
                }else{
                    Toast.makeText(LoginOTPVerification.this,"Please enter verification code",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void signUpMobileOTP(){
        dialogProgress.show();
        retrofit.create(SignUpOTPVerificationAPI.class).post(otp,userId)
                .enqueue(new Callback<SignUpOTPVerificationResponse>() {
                    @Override
                    public void onResponse(Call<SignUpOTPVerificationResponse> call, Response<SignUpOTPVerificationResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status=response.body().getSuccess();
                            if(status.equals("1")){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login", "true");
                                editor.putString("userType", userType);
                                editor.putString("userId", userId);
                                editor.putString("userTypeId", userTypeId);
                                editor.commit();
                                Intent in=new Intent(LoginOTPVerification.this,HomeActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(LoginOTPVerification.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginOTPVerification.this, "Failed", Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpOTPVerificationResponse> call, Throwable t) {
                        Toast.makeText(LoginOTPVerification.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

    private void startSmsRetrever() {
        SmsRetrieverClient client = SmsRetriever.getClient(LoginOTPVerification.this);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("SMS Retriever status", "Success");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("SMS Retriever status", "Failed");
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
           /* String otp=getMessage(message);
            edtOTP.setText(otp);
            Toast.makeText(LoginOTPVerification.this, message, Toast.LENGTH_SHORT).show();*/
        }
    };

    private String getMessage(String message) {
       /* Pattern p = Pattern.compile("\\p{#}\\d{4}\b");*/
        Pattern p = Pattern.compile("\\p{Punct}\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }


    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(LoginOTPVerification.this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(LoginOTPVerification.this).registerReceiver(mMessageReceiver, new IntentFilter("Wishill"));
    }

}
