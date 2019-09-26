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
import com.wishill.wishill.api.recommendedColleges.login.LoginAPI;
import com.wishill.wishill.api.recommendedColleges.login.LoginDetailsData;
import com.wishill.wishill.api.recommendedColleges.login.LoginResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
     TextView tvRegister;
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
    String from;
    TextView tvForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialogProgress = new DialogProgress(LoginActivity.this);
        tvRegister=findViewById(R.id.tv_register);
        edtUserName=findViewById(R.id.edt_user_name);
        edtPassword=findViewById(R.id.edt_password);
        tvSubmit=findViewById(R.id.btn_submit);
        sharedPreferences = getApplicationContext().getSharedPreferences("wishill", MODE_PRIVATE);
        from=getIntent().getStringExtra("from");
        tvForgotPassword=findViewById(R.id.tv_forgot_password);

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

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(LoginActivity.this,RegitrationActivity.class);
                startActivity(in);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=edtUserName.getText().toString().trim();
                password=edtPassword.getText().toString().trim();
                if(!userName.equals("")){
                    if(!password.equals("")){
                        postLoginValues();
                    }else{
                        Toast.makeText(LoginActivity.this, "Please enter login details", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Please enter login details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(LoginActivity.this,PartnerForgotPasswordActivity.class);
                in.putExtra("from","normalUser");
                startActivity(in);
            }
        });
    }

    private void postLoginValues() {
        dialogProgress.show();
        retrofit.create(LoginAPI.class).post(userName,
                password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                            if(status==1){
                                List<LoginDetailsData> detailsList=response.body().getDetailsList();
                                LoginDetailsData loginDetailsData=detailsList.get(0);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login", "true");
                                editor.putString("userType", "normal");
                                editor.putString("userName", loginDetailsData.getU_name());
                                editor.putString("userId", loginDetailsData.getId());
                                editor.putString("userEmail", loginDetailsData.getU_email());
                                editor.putString("userMobile", "");
                                editor.commit();

                                if(from.equals("profile")){
                                    HomeActivity.home.finish();
                                }
                                if(from.equals("details")){
                                    finish();
                                }else{
                                    Intent in=new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(in);
                                }

                            }
                            dialogProgress.dismiss();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

}
