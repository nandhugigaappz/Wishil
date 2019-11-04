package com.wishill.wishill.activity.partnershipwithwishill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wishill.wishill.R;
import com.wishill.wishill.api.recommendedColleges.addPartnerCollege.AddPartnerCollegeAPI;
import com.wishill.wishill.api.recommendedColleges.addPartnerCollege.AddPartnerCollegeResponse;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListAPI;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListData;
import com.wishill.wishill.api.recommendedColleges.getDistrict.DistrictListResponse;
import com.wishill.wishill.api.recommendedColleges.getState.StateListAPI;
import com.wishill.wishill.api.recommendedColleges.getState.StateListData;
import com.wishill.wishill.api.recommendedColleges.getState.StateListResponse;
import com.wishill.wishill.utilities.APILinks;
import com.wishill.wishill.utilities.DialogProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPartnerCollegeActivity extends AppCompatActivity {
    DialogProgress dialogProgress;
    HttpLoggingInterceptor interceptor;
    Gson gson;
    Retrofit retrofit;
    OkHttpClient client;


    String selectedStateName="";
    String selectedStateId="";
    String city="";


    Spinner stateSpinner,citySpinner;
    String institutionName,contactName,contactMobile,contactEmail;
    EditText edtInstitutionName,edtContactName;
    EditText edtMobile;
    TextView tvSubmit;
    String partnerId;

    List<StateListData> stateList;
    List<DistrictListData> cityList;
    List<String> stateNameList;
    List<String> cityNameList;

    String userType;
    TextView tvToolBarTitle;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner_college);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvToolBarTitle=findViewById(R.id.toolbar_title);
        dialogProgress = new DialogProgress(AddPartnerCollegeActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        partnerId = getIntent().getStringExtra("partnerId");
        userType=getIntent().getStringExtra("userType");
        activity=AddPartnerCollegeActivity.this;
        if(userType.equals("1")){
            tvToolBarTitle.setText("Add College");
        }else if(userType.equals("2")){
            tvToolBarTitle.setText("Add School");
        }else if(userType.equals("3")){
            tvToolBarTitle.setText("Add Study Tour");
        }
        edtInstitutionName = findViewById(R.id.edt_institution_name);
        edtContactName = findViewById(R.id.edt_contact_name);
        edtMobile = findViewById(R.id.edt_mobile);
        tvSubmit = findViewById(R.id.btn_submit);
        stateSpinner=findViewById(R.id.state_spinner);
        citySpinner=findViewById(R.id.city_spinner);

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
        getStateList();

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                institutionName = edtInstitutionName.getText().toString().trim();
                contactName = edtContactName.getText().toString().trim();
                contactMobile = edtMobile.getText().toString().trim();
                if(contactMobile.contains("+91")){
                    contactMobile=contactMobile.replace("+91","");
                }
                if (validateInstitutionName()) {
                    if (validateState()) {
                        if (validateCity()) {
                            if (validateContactName()) {
                                if (validateContactMobile()) {
                                    if(userType.equals("1")){
                                        postValuesCollege();
                                    }else if(userType.equals("2")){
                                        postValuesSchool();
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
            Toast.makeText(AddPartnerCollegeActivity.this, "Enter institution name.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateState() {
        if (selectedStateName.equals("")) {
            Toast.makeText(AddPartnerCollegeActivity.this, "Select location details", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCity() {
        if (city.equals("")) {
            Toast.makeText(AddPartnerCollegeActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateContactName() {
        if (contactName.equals("")) {
            Toast.makeText(AddPartnerCollegeActivity.this, "Enter contact person name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateContactMobile() {
        if (contactMobile.equals("")) {
            Toast.makeText(AddPartnerCollegeActivity.this, "Enter contact person mobile", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(contactMobile.contains("+91")){
                return  true;
            }else{
                    Toast.makeText(AddPartnerCollegeActivity.this, "Enter contact person valid mobile", Toast.LENGTH_SHORT).show();
                    return  false;
            }

        }
    }

    //post college values
    private void postValuesCollege() {
        dialogProgress.show();
        retrofit.create(AddPartnerCollegeAPI.class).postCollege(
                institutionName,
                selectedStateId,
                city,
                partnerId,
                contactName,
                contactMobile)
                .enqueue(new Callback<AddPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<AddPartnerCollegeResponse> call, Response<AddPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status = response.body().getSuccess();
                            String message = response.body().getMessage();
                            if(status.equals("1")){
                                finish();
                                Toast.makeText(AddPartnerCollegeActivity.this,message,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddPartnerCollegeActivity.this,message,Toast.LENGTH_SHORT).show();
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


    //post school values
    private void postValuesSchool() {
        dialogProgress.show();
        retrofit.create(AddPartnerCollegeAPI.class).postSchool(
                institutionName,
                selectedStateId,
                city,
                partnerId,
                contactName,
                contactMobile)
                .enqueue(new Callback<AddPartnerCollegeResponse>() {
                    @Override
                    public void onResponse(Call<AddPartnerCollegeResponse> call, Response<AddPartnerCollegeResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String status = response.body().getSuccess();
                            String message = response.body().getMessage();
                            if(status.equals("1")){
                                finish();
                                Toast.makeText(AddPartnerCollegeActivity.this,message,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddPartnerCollegeActivity.this,message,Toast.LENGTH_SHORT).show();
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


    private void getStateList() {
        retrofit.create(StateListAPI.class).post("1")
                .enqueue(new Callback<StateListResponse>() {
                    @Override
                    public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            stateList = response.body().getCatList();
                            stateNameList = new ArrayList<>();
                            stateNameList.add("Select State");
                            for (int i = 0; i < stateList.size(); i++) {
                                stateNameList.add(stateList.get(i).getState());
                            }
                            setStateSpinner();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<StateListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }
    private void setStateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateNameList);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    selectedStateId = stateList.get(i-1).getId();
                    selectedStateName = stateList.get(i-1).getState();
                    getCityList();
                }else{
                    getCityList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCityList() {
        retrofit.create(DistrictListAPI.class).post(selectedStateId)
                .enqueue(new Callback<DistrictListResponse>() {
                    @Override
                    public void onResponse(Call<DistrictListResponse> call, Response<DistrictListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            cityList = response.body().getCatList();
                            cityNameList = new ArrayList<>();
                            cityNameList.add("Select City");
                            for (int i = 0; i < cityList.size(); i++) {
                                cityNameList.add(cityList.get(i).getCity());
                            }
                            setCitySpinner();
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DistrictListResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }


    private void setCitySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityNameList);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    city=cityList.get(i-1).getCity();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent in=new Intent(AddPartnerCollegeActivity.this, SearchPartnerColleges.class);
            in.putExtra("partnerId",partnerId);
            in.putExtra("userType",userType);
            startActivity(in);
            return true;
        }else if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
