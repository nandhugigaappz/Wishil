package com.wishill.wishill.activity.partnershipwithwishill;

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
import com.wishill.wishill.api.recommendedColleges.forgotPartnerPassword.ForgotPartnerPasswordAPI;
import com.wishill.wishill.api.recommendedColleges.forgotPartnerPassword.ForgotPartnerPasswordResponse;
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

public class PartnerForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmail;
    TextView tvSubmit;
    String emailId;

    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(PartnerForgotPasswordActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        from=getIntent().getStringExtra("from");

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

        edtEmail=findViewById(R.id.edt_email);
        tvSubmit=findViewById(R.id.btn_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailId=edtEmail.getText().toString().trim();
                if( isEmailValid()){
                    if(from.equals("partnerUser")){
                        forgotPassword();
                    }else{
                        forgotPasswordNormalUser();
                    }

                }
            }
        });


    }
    private void forgotPassword() {
        dialogProgress.show();
        retrofit.create(ForgotPartnerPasswordAPI.class).post(emailId
        )
                .enqueue(new Callback<ForgotPartnerPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPartnerPasswordResponse> call, Response<ForgotPartnerPasswordResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            Toast.makeText(PartnerForgotPasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                            if(status==1){
                                finish();
                            }
                        } else {
                            Toast.makeText(PartnerForgotPasswordActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPartnerPasswordResponse> call, Throwable t) {
                        Toast.makeText(PartnerForgotPasswordActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }
    private void forgotPasswordNormalUser() {
        dialogProgress.show();
        retrofit.create(ForgotPartnerPasswordAPI.class).normalUser(emailId)
                .enqueue(new Callback<ForgotPartnerPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPartnerPasswordResponse> call, Response<ForgotPartnerPasswordResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            Toast.makeText(PartnerForgotPasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                            if(status==1){
                                finish();
                            }
                        } else {
                            Toast.makeText(PartnerForgotPasswordActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPartnerPasswordResponse> call, Throwable t) {
                        Toast.makeText(PartnerForgotPasswordActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }
    public boolean isEmailValid() {
        if (emailId.equals("")) {
            Toast.makeText(PartnerForgotPasswordActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = emailId;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(PartnerForgotPasswordActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
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

}
