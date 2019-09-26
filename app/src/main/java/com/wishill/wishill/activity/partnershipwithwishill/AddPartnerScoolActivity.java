package com.wishill.wishill.activity.partnershipwithwishill;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.wishill.wishill.api.recommendedColleges.addPartnerCollege.AddPartnerCollegeAPI;
import com.wishill.wishill.api.recommendedColleges.addPartnerCollege.AddPartnerCollegeResponse;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListAPI;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListData;
import com.wishill.wishill.api.recommendedColleges.getCountry.CountryListResponse;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListAPI;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListResponse;
import com.wishill.wishill.api.recommendedColleges.getState.StateListAPI;
import com.wishill.wishill.api.recommendedColleges.getState.StateListData;
import com.wishill.wishill.api.recommendedColleges.getState.StateListResponse;
import com.wishill.wishill.dialogBoxes.ListDialogeBox;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.ArrayList;
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

public class AddPartnerScoolActivity extends AppCompatActivity {
    DialogProgress dialogProgress;

    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;

    TextView tvCountry;
    TextView tvState;
    TextView tvDistrict;

    List<CountryListData> countryList;
    List<StateListData>  stateList;
    List<DistrictListData> districtList;
    List<String> countryNameList;
    List<String> stateNameList;
    List<String> districtNameList;
    public  static  int selectedPosition=100;


    String selectedCountryName;
    String selectedStateName;
    String selectedDistrictName;


    String institutionName,city,street,zipCode,website,contactName,contactMobile,contactEmail;
    String schoolType;
    EditText edtInstitutionName,edtCity,edtStreet,edtZipCode,edtWebsite,edtContactName;
    EditText edtSchoolType;
    EditText edtMobile,edtEmail;
    TextView tvSubmit;
    String partnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner_scool);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogProgress = new DialogProgress(AddPartnerScoolActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        partnerId=getIntent().getStringExtra("partnerId");

        tvCountry=findViewById(R.id.tv_country);
        tvState=findViewById(R.id.tv_state);
        tvDistrict=findViewById(R.id.tv_district);

        edtInstitutionName=findViewById(R.id.edt_institution_name);
        edtSchoolType=findViewById(R.id.edt_school_type);
        edtCity=findViewById(R.id.edt_city);
        edtStreet=findViewById(R.id.edt_street);
        edtZipCode=findViewById(R.id.edt_zip_code);
        edtWebsite=findViewById(R.id.edt_website);
        edtContactName=findViewById(R.id.edt_contact_name);
        edtMobile=findViewById(R.id.edt_mobile);
        edtEmail=findViewById(R.id.edt_email);
        tvSubmit=findViewById(R.id.btn_submit);

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
        getCountryList();

        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(countryList!=null){
                    selectedPosition = 100;
                    ListDialogeBox dialogBox = new ListDialogeBox(AddPartnerScoolActivity.this, countryNameList, "Select Country", "addSchool");
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (selectedPosition != 100) {
                                selectedCountryName = countryList.get(selectedPosition).getCountry();
                                tvCountry.setText(selectedCountryName);
                                tvState.setHint("Select State");
                                tvDistrict.setHint("Select District");
                                tvState.setText("");
                                tvDistrict.setText("");
                                selectedStateName = "";
                                selectedDistrictName = "";
                                getStateList();
                            }
                        }
                    });
                }

            }
        });
        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateList!=null){
                    if(!selectedCountryName.equals("")){
                        selectedPosition = 100;
                        ListDialogeBox dialogBox = new ListDialogeBox(AddPartnerScoolActivity.this, stateNameList, "Select A State", "addSchool");
                        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogBox.show();
                        dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (selectedPosition != 100) {
                                    selectedStateName = stateList.get(selectedPosition).getState();
                                    tvState.setText(selectedStateName);
                                    tvDistrict.setHint("Select District");
                                    tvDistrict.setText("");
                                    selectedDistrictName = "";
                                    getDistrictList();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(AddPartnerScoolActivity.this,"Select a country",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        tvDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(districtNameList!=null){
                    if(!selectedStateName.equals("")){
                        selectedPosition = 100;
                        ListDialogeBox dialogBox = new ListDialogeBox(AddPartnerScoolActivity.this, districtNameList, "Select A District", "addSchool");
                        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogBox.show();
                        dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (selectedPosition != 100) {
                                    selectedDistrictName = districtList.get(selectedPosition).getCity();
                                    tvDistrict.setText(selectedDistrictName);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(AddPartnerScoolActivity.this,"Select a state",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                institutionName=edtInstitutionName.getText().toString().trim();
                schoolType=edtSchoolType.getText().toString().trim();
                city=edtCity.getText().toString().trim();
                street=edtStreet.getText().toString().trim();
                zipCode=edtZipCode.getText().toString().trim();
                website=edtWebsite.getText().toString().trim();
                contactName=edtContactName.getText().toString().trim();
                contactMobile=edtMobile.getText().toString().trim();
                contactEmail=edtEmail.getText().toString().trim();
                if(validateInstitutionName()){
                    if(validateSchoolType()) {
                        if (validateLocation()) {
                            if (validateCity()) {
                                if (validateStreet()) {
                                    if (validateZipCode()) {
                                        if (validateWebsite()) {
                                            if (validateContactName()) {
                                                if (validateContactMobile()) {
                                                    if (validateContactEmail()) {

                                                       // postValuesCollege();
                                                    }
                                                }

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }


            }
        });


    }
    private boolean validateInstitutionName() {
        if (institutionName.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter school name.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateSchoolType() {
        if (schoolType.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter school type.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateLocation() {
        if (selectedDistrictName.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Select location details", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCity() {
        if (city.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateStreet() {
        if (street.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter Street", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateZipCode() {
        if (zipCode.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter Street", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateWebsite() {
        if (website.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter website", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateContactName() {
        if (contactName.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter contact person name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateContactMobile() {
        if (contactMobile.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Enter contact person mobile", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(contactMobile.length()==10){
                return true;
            }else{
                Toast.makeText(AddPartnerScoolActivity.this, "Enter contact person valid mobile", Toast.LENGTH_SHORT).show();
                return  false;
            }
        }
    }

    private boolean validateContactEmail() {
        if (contactEmail.equals("")) {
            Toast.makeText(AddPartnerScoolActivity.this, "Email required.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = contactEmail;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(AddPartnerScoolActivity.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }


 /*   private void postValuesCollege() {
        dialogProgress.show();
        retrofit.create(AddPartnerCollegeAPI.class).postSchool(
                institutionName,
                schoolType,
                street,
                zipCode,
                selectedCountryName,
                selectedStateName,
                selectedDistrictName,
                city,
                contactName,
                contactMobile,
                contactEmail,
                website,
                partnerId
        )
                .enqueue(new Callback<AddPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<AddPartnerCollegeResponse> call, Response<AddPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status = response.body().getSuccess();
                            String message = response.body().getMessage();
                            if(status.equals("1")){
                                finish();
                                Toast.makeText(AddPartnerScoolActivity.this,message,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddPartnerScoolActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPartnerCollegeResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
*/


    private void getCountryList() {
        retrofit.create(CountryListAPI.class).post("")
                .enqueue(new Callback<CountryListResponse>() {
                    @Override
                    public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                        if (response.isSuccessful()) {
                            countryList=response.body().getCatList();
                            countryNameList = new ArrayList<>();
                            for (int i = 0; i < countryList.size(); i++) {
                                countryNameList.add(countryList.get(i).getCountry());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryListResponse> call, Throwable t) {

                    }
                });
    }
    private void getStateList() {
        dialogProgress.show();
        retrofit.create(StateListAPI.class).post(selectedCountryName)
                .enqueue(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            stateList=response.body().getCatList();
                            stateNameList = new ArrayList<>();
                            for (int i = 0; i < stateList.size(); i++) {
                                stateNameList.add(stateList.get(i).getState());
                            }
                        }else{
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void getDistrictList() {
        dialogProgress.show();
        retrofit.create(DistrictListAPI.class).post(selectedStateName)
                .enqueue(new Callback<DistrictListResponse>() {
                    @Override
                    public void onResponse(Call<DistrictListResponse> call, Response<DistrictListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            districtList=response.body().getCatList();
                            districtNameList = new ArrayList<>();
                            for (int i = 0; i < districtList.size(); i++) {
                                districtNameList.add(districtList.get(i).getCity());
                            }
                        }else{
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<DistrictListResponse> call, Throwable t) {
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
