package com.wishill.wishill.activity;

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
import com.wishill.wishill.api.recommendedColleges.partnerChangePassword.PartnerChangePasswordAPI;
import com.wishill.wishill.api.recommendedColleges.partnerChangePassword.PartnerChangePasswordResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import com.wishill.wishill.utilities.PasswordValidation;
import com.wishill.wishill.utilities.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    EditText edtPasswordOne;
    EditText edtPasswordTwo;
    TextView tvButtonSubmit;

    SharedPreferences sharedPreferences;
    String userID;
    String passwordOne,passwordTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(ChangePasswordActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        sharedPreferences =getApplicationContext().getSharedPreferences("wishill",MODE_PRIVATE);
        userID=sharedPreferences.getString("userId","");


        edtPasswordOne=findViewById(R.id.edt_pass_one);
        edtPasswordTwo=findViewById(R.id.edt_pass_two);
        tvButtonSubmit=findViewById(R.id.btn_submit);

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
        tvButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordOne=edtPasswordOne.getText().toString().trim();
                passwordTwo=edtPasswordTwo.getText().toString().trim();
                if(validatePassword()){
                    changePassword();
                }
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
    private void changePassword() {
        dialogProgress.show();
        retrofit.create(PartnerChangePasswordAPI.class).normalUser(
                userID,
                passwordOne)
                .enqueue(new Callback<PartnerChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<PartnerChangePasswordResponse> call, Response<PartnerChangePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PartnerChangePasswordResponse> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }
    private boolean validatePassword() {
        if (passwordOne.equals("")) {
            Toast.makeText(ChangePasswordActivity.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            ArrayList<PasswordValidation> arrayList = Utils.validatePassword(passwordOne);
            if (passwordOne.contains(" ")) {
                Toast.makeText(ChangePasswordActivity.this, "Please enter a Password without space", Toast.LENGTH_SHORT).show();
                return false;
            }
           /* if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                Toast.makeText(PartnerChangePasswordActivity.this, "Password should be Minimum 6 characters length.Minimum 1 Uppercase, Minimum 1 number, Minimum 1 lowercase", Toast.LENGTH_SHORT).show();
                return false;
            }*/
            if (!passwordOne.equals(passwordTwo)) {
                Toast.makeText(ChangePasswordActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

}
