package com.wishill.wishill.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.wishill.wishill.api.recommendedColleges.regitration.RegistrationAPI;
import com.wishill.wishill.api.recommendedColleges.regitration.RegistrationResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;
import com.wishill.wishill.utilities.PasswordValidation;
import com.wishill.wishill.utilities.Utils;

import java.util.ArrayList;
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

public class RegitrationActivity extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    RadioGroup radioGroup;
    RadioButton rbMale;
    RadioButton rbFemale;
    EditText edtEmail;
    EditText edtMobile;
    EditText edtPasswordOne;
    EditText edtPasswordTwo;
    TextView btnSubmit;
    String firstName,lastName,email,mobile,passwordOne,passwordTwo;
    String gender="Male";
    public Dialog alertDialog;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;
    DialogProgress dialogProgress;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(RegitrationActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        edtFirstName=findViewById(R.id.edt_first_name);
        edtLastName=findViewById(R.id.edt_last_name);
        radioGroup=findViewById(R.id.radio_group_selection_type);
        rbMale=findViewById(R.id.rb_male);
        rbFemale=findViewById(R.id.rb_male);
        edtEmail=findViewById(R.id.edt_email_address);
        edtMobile=findViewById(R.id.edt_mobile);
        edtPasswordOne=findViewById(R.id.edt_pass_one);
        edtPasswordTwo=findViewById(R.id.edt_pass_two);
        btnSubmit=findViewById(R.id.btn_submit);
        tvLogin=findViewById(R.id.tv_login);

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


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    gender="Male";
                } else {
                   gender="Female";
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName=edtFirstName.getText().toString().trim();
                lastName=edtLastName.getText().toString().trim();
                email=edtEmail.getText().toString().trim();
                mobile=edtMobile.getText().toString().trim();
                passwordOne=edtPasswordOne.getText().toString().trim();
                passwordTwo=edtPasswordTwo.getText().toString().trim();
                if(validateFirstName()){
                    if(validateSecondName()){
                        if(isEmailValid()){
                            if(validatePhone()){
                                 if(validatePassword()){
                                     dialogProgress.show();
                                     String date=Utils.convertDateFormat(Utils.currentDateAndTime());
                                     postSignUpValues(date);

                                 }
                            }
                        }
                    }
                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void postSignUpValues(String date) {
        retrofit.create(RegistrationAPI.class).post(firstName,
                lastName,
                email,
                gender,
                mobile,
                passwordOne,
                date)
                .enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        if (response.isSuccessful()) {
                            int status=response.body().getSuccess();
                            String message=response.body().getMessage();
                            dialogProgress.dismiss();
                            alertSuccess(status,message);
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
    //validation
    private boolean validateFirstName() {
        if (firstName.equals("") || (!firstName.matches("[a-zA-Z]+"))) {
            Toast.makeText(RegitrationActivity.this, "Enter first name without space. Numbers not allowed.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSecondName() {
        if (lastName.equals("") || (!lastName.matches("^[\\p{L} .'-]+$"))) {
            Toast.makeText(RegitrationActivity.this, "Enter last name. Numbers not allowed.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmailValid() {
        if (email.equals("")) {
            Toast.makeText(RegitrationActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(RegitrationActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }


    private boolean validatePhone() {
        if (mobile.equals("")) {
            Toast.makeText(RegitrationActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mobile.length()!=10) {
                Toast.makeText(RegitrationActivity.this, "Please enter a valid mobile number",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private boolean validatePassword() {
        if (passwordOne.equals("")) {
            Toast.makeText(RegitrationActivity.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            ArrayList<PasswordValidation> arrayList = Utils.validatePassword(passwordOne);
            if (passwordOne.contains(" ")) {
                Toast.makeText(RegitrationActivity.this, "Please enter a Password without space", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                Toast.makeText(RegitrationActivity.this, "Password should be Minimum 6 characters length.Minimum 1 Uppercase, Minimum 1 number, Minimum 1 lowercase", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passwordOne.equals(passwordTwo)) {
                Toast.makeText(RegitrationActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
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


    private void alertSuccess(int status, String successMessage) {
        alertDialog = new Dialog(RegitrationActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        ImageView alertTitle=alert_layout.findViewById(R.id.alertTitle);
        if(status==1){
            alertTitle.setImageResource(R.drawable.icon_alert_success);
            title.setText("Success");
        }else{
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
