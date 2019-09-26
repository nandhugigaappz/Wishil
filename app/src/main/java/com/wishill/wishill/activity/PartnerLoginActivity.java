package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.activity.partnershipwithwishill.PartnerForgotPasswordActivity;
import com.wishill.wishill.activity.partnershipwithwishill.PartnerShipRegisterActivity;
import com.wishill.wishill.api.recommendedColleges.partnerLogin.PartnerLoginAPI;
import com.wishill.wishill.api.recommendedColleges.partnerLogin.PartnerResponse;
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

public class PartnerLoginActivity extends AppCompatActivity {
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    EditText edtUserName;
    EditText edtPassword;
    String userName;
    String password;
    TextView tvSubmit;
    SharedPreferences sharedPreferences;
    TextView tvForgotPassword;
    TextView tvCreatePartner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_login);
        dialogProgress = new DialogProgress(PartnerLoginActivity.this);
        edtUserName=findViewById(R.id.edt_user_name);
        edtPassword=findViewById(R.id.edt_password);
        tvSubmit=findViewById(R.id.btn_submit);
        tvForgotPassword=findViewById(R.id.tv_forgot_password);
        tvCreatePartner=findViewById(R.id.tv_create_partner);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);

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
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=edtUserName.getText().toString().trim();
                password=edtPassword.getText().toString().trim();
                if(!userName.equals("")){
                    if(!password.equals("")){
                        postLoginValues();
                    }else{
                        Toast.makeText(PartnerLoginActivity.this, "Please enter login details", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PartnerLoginActivity.this, "Please enter login details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(PartnerLoginActivity.this,PartnerForgotPasswordActivity.class);
                in.putExtra("from","partnerUser");
                startActivity(in);
            }
        });
        tvCreatePartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(PartnerLoginActivity.this,PartnerShipRegisterActivity.class);
                startActivity(in);
            }
        });

    }
    private void postLoginValues() {
        dialogProgress.show();
        retrofit.create(PartnerLoginAPI.class).post(userName,
                password)
                .enqueue(new Callback<PartnerResponse>() {
                    @Override
                    public void onResponse(Call<PartnerResponse> call, Response<PartnerResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status=response.body().getSuccess();
                            if(status.equals("1")){
                                String userID=response.body().getData().getUserId();
                                String userTypeId=response.body().getData().getPartnertypeID();
                                String otpVerity=response.body().getData().getOtpVerify();
                                String mobileNumber=response.body().getData().getMobileNumber();
                                if(otpVerity.equals("false")){
                                    Intent in=new Intent(PartnerLoginActivity.this,LoginOTPVerification.class);
                                    in.putExtra("mobileNumber",mobileNumber);
                                    in.putExtra("userId",userID);
                                    in.putExtra("userType","partner");
                                    in.putExtra("userTypeId",userTypeId);
                                    startActivity(in);
                                    finish();
                                }else{
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("login", "true");
                                    editor.putString("userType", "partner");
                                    editor.putString("userId", userID);
                                    editor.putString("userTypeId", userTypeId);
                                    editor.commit();
                                    Intent in=new Intent(PartnerLoginActivity.this,HomeActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    finish();
                                }
                            }else{
                                Toast.makeText(PartnerLoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PartnerLoginActivity.this,"Failed to login.",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<PartnerResponse> call, Throwable t) {
                        Toast.makeText(PartnerLoginActivity.this,"Failed to login.",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

}
