package com.wishill.wishill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobile.SignUpMobileAPI;
import com.wishill.wishill.api.recommendedColleges.SignUpWithMobile.SignUpMobileResponse;
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

import static com.wishill.wishill.activity.SocialMediaActivity.referrer;

public class MobileNumberActivity extends AppCompatActivity {
     EditText edtMobileNumber;
     TextView btnSubmit;
     String mobileNumber;
    SharedPreferences sharedPreferences;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        edtMobileNumber=findViewById(R.id.edt_mobile_number);
        btnSubmit=findViewById(R.id.btn_submit);
        ivBack=findViewById(R.id.iv_back);
        dialogProgress=new DialogProgress(MobileNumberActivity.this);
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



        edtMobileNumber.setText("+91 ");
        Selection.setSelection(edtMobileNumber.getText(), edtMobileNumber.getText().length());

        edtMobileNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("+91 ")){
                    edtMobileNumber.setText("+91 ");
                    Selection.setSelection(edtMobileNumber.getText(), edtMobileNumber.getText().length());

                }

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber=edtMobileNumber.getText().toString().trim();
                mobileNumber=mobileNumber.replace("+91 ","");
                if(!mobileNumber.equals("")&&mobileNumber.length()==10){
                    signUpMobile();
                }else{
                    Toast.makeText(MobileNumberActivity.this,"Enter a valid mobile number",Toast.LENGTH_SHORT).show();
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

    private void signUpMobile(){
        dialogProgress.show();
        retrofit.create(SignUpMobileAPI.class).post(mobileNumber,
                referrer/*,
                referredInstitute,
                referredInstituteType*/)
                .enqueue(new Callback<SignUpMobileResponse>() {
                    @Override
                    public void onResponse(Call<SignUpMobileResponse> call, Response<SignUpMobileResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status=response.body().getSuccess();
                            if(status.equals("1")){
                                Intent in=new Intent(MobileNumberActivity.this,LoginOTPVerification.class);
                                in.putExtra("mobileNumber",mobileNumber);
                                in.putExtra("userId",response.body().getData().getUserID());
                                in.putExtra("userType","normal");
                                in.putExtra("userTypeId","0");
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(MobileNumberActivity.this,"Failed to register",Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(MobileNumberActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpMobileResponse> call, Throwable t) {
                        Toast.makeText(MobileNumberActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }
}
